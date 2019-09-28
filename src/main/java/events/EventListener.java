package events;

import java.awt.Color;
import java.util.List;



import commands.Mute;
import commands.VirtualChannel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter{

	private char prefix='§';
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if(!event.getAuthor().isBot()) {
			
			//event.getChannel().sendMessage("working...").complete();
			if(event.getMessage().getContentDisplay().equalsIgnoreCase("silenzio in aula")&&event.getMember().hasPermission(Permission.ADMINISTRATOR)) Mute.muteinvc(event);
			String[] args=event.getMessage().getContentRaw().split(" ");
			
			//System.out.println(args[0]+args[1]);
		
			if(args[0].equalsIgnoreCase(prefix+"changeprefix")) {prefix=args[1].charAt(0);changeprefmsg(event.getChannel(),prefix);}
			
			
			if(event.getMessage().getContentRaw().contains("529291563239604234")) help(event.getChannel());
			if(args[0].equalsIgnoreCase(prefix+"purge")) {
				if(event.getMember().hasPermission(Permission.ADMINISTRATOR)) {

				Member rem=event.getMessage().getMentionedMembers().get(0);
				TextChannel txt =event.getChannel();
				TextChannel target=event.getMessage().getMentionedChannels().get(0);
				int i=0;
				System.out.println("vado");
				MessageHistory hyst=event.getMessage().getMentionedChannels().get(0).getHistory();
		
				List<Message> msgs=hyst.retrievePast(Integer.parseInt(args[2])).complete();
		
				for(Message msg:msgs) {
					if(msg.getMember().equals(rem)) {msg.delete().queue();i++;}
			
				}
				dellog(rem,event.getMember(),String.valueOf(i),target);
				dellog(rem,event.getMember(),String.valueOf(i),txt);
		
			}else event.getChannel().sendMessage("you do not have permission to do that").queue();}

			if(args[0].equalsIgnoreCase(prefix+"createvchannel")) 
				VirtualChannel.createvchannel(event);
			
			if(args[0].equalsIgnoreCase(prefix+"removevchannel")) 
				VirtualChannel.removevchannel(event);
			
			if(args[0].equalsIgnoreCase(prefix+"adduservchannel")) 
				VirtualChannel.adduservchannel(event);
			
	
		}

		
	}
	private void changeprefmsg(TextChannel txt,char prefix) {
		EmbedBuilder builder= new EmbedBuilder();
		builder.setTitle("PREFIX CHANGED");
		builder.setColor(Color.CYAN);
		builder.addField("**The new prefix is:** ",String.valueOf(prefix) , false);
		
		txt.sendMessage(builder.build()).complete();
		
	}
	private void help(TextChannel txt) {
		EmbedBuilder builder= new EmbedBuilder();
		builder.setTitle(" __**HELP:**__");
		//StringBuilder desc=builder.getDescriptionBuilder();
		builder.getDescriptionBuilder().append("**"+prefix+"createvchannel   @user1   @user2...**\n"
				+ "crea un canale virtuale(invisibile) per gli utenti specificati che si autoelimina quando l'ultimo esce\n\n");
		builder.getDescriptionBuilder().append("**"+prefix+"removevchannel   @user1   @user2...**\n"
				+ "cancella il tuo canale virtuale\n\n");
		builder.getDescriptionBuilder().append("**"+prefix+"adduservchannel   @user1   @user2...**\n"
				+ "aggiunge l'utente specificato al canale virtuale\n\n");
		builder.getDescriptionBuilder().append("**"+prefix+"purge   @user   numeromessaggi**\n"
				+ "elimina dagli ultimi N messaggi, i messaggi di @user (solo per admin)");
		
		builder.setColor(Color.YELLOW);
		
		

		txt.sendMessage(builder.build()).complete();
		
	}
	@Override
	public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
		try {
			System.out.println("channel into category: "+event.getChannelLeft().getParent().getName());
		}catch(NullPointerException e) {System.out.println("channel out of category");return;}
		if(event.getChannelLeft().getParent().getName().equalsIgnoreCase("Private Channels")) {
		if(event.getChannelLeft().getMembers().isEmpty())event.getChannelLeft().delete().queue();
		}
		
	}
	




	



	public void dellog(Member usdel,Member author,String msgdel,TextChannel txt) {
		EmbedBuilder builder= new EmbedBuilder();
		builder.setTitle("Deleted User Messages");
		builder.setColor(Color.GREEN);
		builder.addField("User Deleted:", usdel.getAsMention(), false);
		builder.addField("By:", author.getAsMention(), false);
		builder.addField("Messages deleted:", msgdel, false);

		txt.sendMessage(builder.build()).queue();
	}
	
	

}
