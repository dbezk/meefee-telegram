package com.meefee.telegram.bot.constant;

public class BotMessages {

    public static String UNKNOWN_COMMAND = """
            ❗️ Unknown command!
            
            Send an authorization code.
            """;

    public static String START_COMMAND = """
            👋🏻 Hi, <b>%s</b>!
            
            Send an authorization code.
            """;

    public static String UNKNOWN_MESSAGE_TYPE = """
            ❗️ Unknown message type!
            
            Send an authorization code.
            """;

    public static String INVALID_AUTHORIZATION_CODE = """
            ❗️ Invalid authorization code!
            
            Send a valid authorization code.
            """;

    public static String AUTHORIZATION_CODE_NOT_FOUND_OR_INVALID = """
            ❗️ Authorization code not found or was expired
            
            Go to authorization page and try again.
            """;

}
