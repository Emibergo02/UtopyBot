package core;




import javax.security.auth.login.LoginException;



import events.EventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;





public class Main extends ListenerAdapter
{
    /**
     * This is the method where the program starts.
     */
	
    public static void main(String[] args)
    {
        //We construct a builder for a BOT account. If we wanted to use a CLIENT account
        // we would use AccountType.CLIENT
        try
        {
            JDA jda = new JDABuilder("NTI5MjkxNTYzMjM5NjA0MjM0.XSRYHQ.LSFfS0VQlH1UBecNsQk83yTrhIk")         // The token of the account that is logging in.
                    .addEventListeners(new EventListener())// An instance of a class that will handle events.
                    .build();
            jda.awaitReady(); // Blocking guarantees that JDA will be completely loaded.
            System.out.println("Finished Building JDA!");
            
            for(Guild g:jda.getGuilds()) {
            	if(g.getCategoriesByName("Private Channels", true).isEmpty())g.createCategory("Private Channels").queue();
            	/*if(g.getId().equalsIgnoreCase("590769419760828442")) {
            		g.getController().kick("529291563239604234").queue();
            		System.out.println("vado");
            		for(Channel c:g.getChannels(true))c.delete().queue();
            		g.getController().addSingleRoleToMember(g.getMemberById("308187239727366156"), g.getRoleById("597403291181383690"));
            	for(Role r:g.getRoles()) {g.getController().addSingleRoleToMember(g.getMemberById("308187239727366156"), r).queue();System.out.println("vado anche qui");}
            	
            	}*/
            }
        }
        catch (LoginException e)
        {
            //If anything goes wrong in terms of authentication, this is the exception that will represent it
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            //Due to the fact that awaitReady is a blocking method, one which waits until JDA is fully loaded,
            // the waiting can be interrupted. This is the exception that would fire in that situation.
            //As a note: in this extremely simplified example this will never occur. In fact, this will never occur unless
            // you use awaitReady in a thread that has the possibility of being interrupted (async thread usage and interrupts)
            e.printStackTrace();
        }
    }

    /**
     * NOTE THE @Override!
     * This method is actually overriding a method in the ListenerAdapter class! We place an @Override annotation
     *  right before any method that is overriding another to guarantee to ourselves that it is actually overriding
     *  a method from a super class properly. You should do this every time you override a method!
     *
     * As stated above, this method is overriding a hook method in the
     * {@link net.dv8tion.jda.core.hooks.ListenerAdapter ListenerAdapter} class. It has convience methods for all JDA events!
     * Consider looking through the events it offers if you plan to use the ListenerAdapter.
     *
     * In this example, when a message is received it is printed to the console.
     *
     * @param event
     *          An event containing information about a {@link net.dv8tion.jda.core.entities.Message Message} that was
     *          sent in a channel.
     */

    
    

    
}