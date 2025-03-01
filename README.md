# always_enough_toilet_paper


# Purpose
The purpose of this application is to make it easier for people who live in a shared appartment to manage shooping. This will be made easier with a shared shopping list where people that live together can add items that needs to be bought. Then if anyone buys it it will be marked as bought and removed from the shared shopping list so everyone knows it has been bought, and you avoid buying too much of one item. This will likely also reduce the chance of a household running out of toilet paper. The application will also track who owes who how much money.

# Requirements
- [x] 1. As a user I want to be able to create a new household group with a name so I can start a new household. 
- [x] 2. As a household creator, I want my household to have a unique code so that other users can join it. 
- [x] 3. As a user I want to be able to join an existing household group by entering the household code so that I can participate in the household management. 
- [x] 4. As a user I want to be able to log in using my Google account so that unauthorized users cannot see my information. 
- [x] 5. As a user I want to be able to put items on a shared shopping list so that everybody in the household knows what items are missing. 
- [x] 6. As a user I want to be able to select items in the shopping list that I have bought so they will be automatically moved to history so the shopping list can be updated, and others can see that it has been bought.
- [x] 7. As a user I want to be able to delete the items in the shopping list to keep it up to date. 
- [x] 8. As a user I want my name in the household to be set to my Google account name so that others can recognize me without me having to change it.  
- [x] 9. As a user I want to be able to change my household user nickname so that others in the household can recognize me. 
- [x] 10. As a user I want to be able to enter my phone number for other users to be able to pay their debt to me. 
- [x] 11. As a user I want to be able to change my phone number for other users to be able to keep the information up to date. 
- [x] 12. As a user I want to be able to see the history of items that have been bought together with the username who bought them to get an overview of who bought what. 
- [x] 13. As a user I want to be able to clear the history of bought items if it is not relevant anymore. 
- [x] 14. As a user I want the displayed data to be automatically live updated based on changes in the database. 
- [x] 15. As a user I want to be able to leave a household whenever I am not part of it anymore.
- [x] 16. As a household creator, I want the household to be deleted when I leave it if there are no other members. 
- [x] 17. As a household creator, I want the creator to be signed to another member when I leave the household if there are other members.
- [x] 18. As a user, I want to be able to change the name of a household so that it will be easier to recognize. 
- [ ] 19. As a user I want to enter the price of the bought item so that others know how much they owe me.
- [ ] 20. As a user I want to get notified when somebody from the household adds an item to the shopping list so that I know what should be bought. 
- [ ] 21. As a user I want to get notified when somebody from the household buys an item from the shopping list to know not to buy it as well. 
- [ ] 22. As a user I want to be able to change what notification I am getting in order to only receive what I choose to. 
- [ ] 23. As a user I want to see how much I owe other people in the household in order to know whether I have to send them money. 
- [ ] 24. As a user I want to be able to enter the amount of money and the user I have transferred the money to in a household so that my debt can be updated.
- [ ] 25. As a user I want to be able to confirm or decline somebody’s payment claim in order to avoid cheating. 
- [ ] 26. As a user I want the app to automatically reduce my debt by the amount I have paid and was confirmed in order to keep track of the current debt. 
- [ ] 27. As a user I want to see the payment history of a household to get an overview of who paid how much when.
- [ ] 28. As a household creator, I want to be able to remove other users from the household if the user moves out.

# YouTube link
[Application demonstration](https://www.youtube.com/watch?v=S-mbcxoQDX4)

# Issues with dependencies version

Firebase Auth, Failed resolution of: Lcom/google/android/gms/auth/api/credentials/CredentialsOptions$Builder

https://stackoverflow.com/questions/78103881/firebase-auth-failed-resolution-of-lcom-google-android-gms-auth-api-credential

UPDATE:

Changing

implementation("com.google.android.gms:play-services-auth:21.0.0")

to

implementation("com.google.android.gms:play-services-auth:20.5.0")

fixed the error. does anyone know why?


# Setup development in new IDE

Get SHA-1 key and add it to firebase

In the Gradel sidebar

run gradle signingReport

https://stackoverflow.com/questions/39144629/how-to-add-sha-1-to-android-application
