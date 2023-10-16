package net.playground.documentsigningservice.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * The ValidationException is thrown by an application when the user has submitted a value that is not valid for the respective property.
 *
 * One ValidationException can have multiple messages.
 */
public class ValidationException extends RuntimeException {

    private List<String> messageList;

    private void initMessageList() {
        messageList = new ArrayList<>(32);
    }

    public ValidationException() {
        initMessageList();
    }

    public ValidationException(String message) {
        super(message);
        initMessageList();
        messageList.add(message);
    }

    /**
     * Adds the specified messages to this ValidationException object.
     * @param message the message(s) to add.
     */
    public void addMessage(String... message) {
        Stream.of(message).forEach(s -> messageList.add(s));
    }

    /**
     * The messages list
     * @return a List of Strings representing the messages, or an empty collection when there are no messages
     */
    public List<String> getMessages() {
        return messageList;
    }

    /**
     * Returns all messages as a csv string value.
     * @return messages seperated by ';', or an empty String when there are no messages.
     */
    public String getMessagesAsString() {
        StringBuilder sb = new StringBuilder();
        messageList.forEach(s -> {
            sb.append(s).append(";");
        });
        return sb.toString();
    }

    /**
     * Indicates whether messages are defined for this ValidationException.
     * No messages means up until now everything should be valid.
     *
     * @return true when there are no messages defined, false otherwise.
     */
    public boolean validationOK() {
        return this.messageList.isEmpty();
    }
}
