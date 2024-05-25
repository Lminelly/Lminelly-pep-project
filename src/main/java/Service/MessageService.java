package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {

    private final MessageDAO messageDAO;

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message) {
        return messageDAO.createMessage(message);
    }

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public List<Message> getAllMessagesByUserId(int userId) {
        return messageDAO.getAllMessagesByUserId(userId);
    }

    public boolean deleteMessage(int messageId) {
        return messageDAO.deleteMessage(messageId);
    }

    public boolean updateMessage(Message message) {
        return messageDAO.updateMessage(message);
    }
}
