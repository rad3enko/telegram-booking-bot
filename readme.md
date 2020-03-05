# Telegram booking bot
Book services at time you want and be notified of upcoming book.

## Instruction
1. Get telegram bot `name` and `token` from @BotFather
2. Create new postgresql database
3. Set environment variables
4. `mvn clean package`
5. `java -jar *path-to-jar*.jar`

### Environment variables
##### Mandatory

*$TELEGRAM_BOT_NAME*    
*$TELEGRAM_BOT_TOKEN*   
*$DB_USERNAME*  
*$DB_PASSWORD*  
*$DATABASE_URL* - jdbc db url (ex. `jdbc:postgresql://localhost:5432/my_db`)    

##### Optional
in further releases

`Java` `telegrambots` `Spring Boot` `PostgreSQL`