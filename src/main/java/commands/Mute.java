package commands;

import java.util.List;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;



public class Mute {

	public static void muteinvc(GuildMessageReceivedEvent event) {
		Member autore=event.getMember();
    	
    	if(!autore.getUser().isBot()&&event.getMessage().getContentDisplay().contains("silenzio in aula")) {
        
        VoiceChannel vc=autore.getVoiceState().getChannel();
        try{
        	List<Member> members=vc.getMembers();
        	for(Member user:members) {
    		if(!user.isOwner()&&!user.equals(autore)) {
    			user.getGuild().mute(user, true).queue();
    			
    			
    			}
    		
    		
        	}
        	event.getChannel().sendMessage("Users **muted** in channel `"+vc.getName()+"`").queue();
        	unmute(members,event.getChannel());
        }catch(NullPointerException e) {
        	event.getChannel().sendMessage("no users to tempmute").queue();
        	return;
        }
    	
    }
	}

	private static void unmute(List<Member> members,TextChannel txt) {
		Thread thread;
		thread=new Thread() {
			public void run() {
				String s=txt.sendMessage("unmuted in").complete().getId();
				for(int i=10;i>0;i--) {
					txt.editMessageById(s, "unmuted in "+String.valueOf(i)).queue();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						System.out.println(e);
					}
				}
				txt.deleteMessageById(s).queue();
				for(Member m:members) {
					m.getGuild().mute(m, false).queue();
				}
				txt.sendMessage("**Unmuted**").queue();
			}
		};
		thread.start();
	}
}
