# Telegram booking bot
Book services at time you want and be notified of upcoming book.

## Screenshot
<img src="https://user-images.githubusercontent.com/10897930/79995216-a03a0b80-84bf-11ea-9b17-bafdce536309.jpg" width ="360" height="640">
## Instruction
1. Get telegram bot `name` and `token` from @BotFather
2. Create new postgresql database
3. Set environment variables
4. `mvn clean package`
5. `java -jar *path-to-jar*.jar`
6. message `/start` to your bot

### Environment variables
##### Mandatory

*$TELEGRAM_BOT_NAME*    
*$TELEGRAM_BOT_TOKEN*   
*$DB_USERNAME*  
*$DB_PASSWORD*  
*$DATABASE_URL* - jdbc db url (ex. `jdbc:postgresql://localhost:5432/my_db`)    

##### Optional
in further releases

### Network exception solution
While running bot on local servers located in areas where direct access to telegram servers is not allowed, there is a solution on how to run bot using Tor browser.

Start Tor and modify `Launcher` class:
```
public static void main(String[] args) {
    System.getProperties().put("proxySet", "true");
    System.getProperties().put("socksProxyHost", "127.0.0.1");
    System.getProperties().put("socksProxyPort", "9150");
    
    ApiContextInitializer.init();
    SpringApplication.run(Launcher.class, args);
}
```
    
`Java` `telegrambots` `Spring Boot` `PostgreSQL`