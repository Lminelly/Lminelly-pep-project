package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;




/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    

    private final AccountService accountService;
    private final MessageService messageService;
    private final ObjectMapper objectMapper;


    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
        this.objectMapper =  new ObjectMapper();
    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::register);
        app.post("/login", this::login);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/:message_id", this::getMessageById);
        app.delete("/messages/:message_id", this::deleteMessage);
        app.patch("/messages/:message_id", this::updateMessage);
        app.get("/accounts/:account_id/messages", this::getMessagesByUserId);
        return app;
    }
    private void register(Context ctx) {
        try {
            Account account = objectMapper.readValue(ctx.body(), Account.class);
            account = accountService.createAccount(account);
            ctx.json(account);
        } catch (IOException e) {
            ctx.status(400).result("Bad Request");
        }
    }

    private void login(Context ctx) {
        try {
            Account account = objectMapper.readValue(ctx.body(), Account.class);
            Account loggedInAccount = accountService.getAccountByUsername(account.getUsername());
            if (loggedInAccount != null && loggedInAccount.getPassword().equals(account.getPassword())) {
                ctx.json(loggedInAccount);
            } else {
                ctx.status(401).result("Unauthorized");
            }
        } catch (IOException e) {
            ctx.status(400).result("Bad Request");
        }
    }
    
    private void createMessage(Context ctx) {
        try {
            Message message = objectMapper.readValue(ctx.body(), Message.class);
            message = messageService.createMessage(message);
            ctx.json(message);
        } catch (IOException e) {
            ctx.status(400).result("Bad Request");
        }
    }

    private void getAllMessages(Context ctx) {
        ctx.json(messageService.getAllMessages());
    }

    private void getMessageById(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            ctx.json(message);
        } else {
            ctx.status(404).result("Not Found");
        }
    }

    private void deleteMessage(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        boolean deleted = messageService.deleteMessage(messageId);
        if (deleted) {
            ctx.status(200);
        } else {
            ctx.status(404).result("Not Found");
        }
    }

    private void updateMessage(Context ctx) {
        try {
            int messageId = Integer.parseInt(ctx.pathParam("message_id"));
            Message message = objectMapper.readValue(ctx.body(), Message.class);
            message.setMessage_id(messageId);
            boolean updated = messageService.updateMessage(message);
            if (updated) {
                ctx.json(message);
            } else {
                ctx.status(400).result("Bad Request");
            }
        } catch (IOException e) {
            ctx.status(400).result("Bad Request");
        }
    }
    
    private void getMessagesByUserId(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(messageService.getAllMessagesByUserId(accountId));
    }
}