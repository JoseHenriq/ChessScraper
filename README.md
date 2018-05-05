# ChessScraper
05/05/2018

Web scraper designed for chess.com

This scraper allows you to get PGN games from chess.com
You can get PGNs both from games that have finished and that are being played live. So far there is no .jar file you can use. 
I only have the source code which works from Netbeans, I need to figure out what the heck is going on with the compiled .jar

This Java app uses Selenium and a very basic java Timer to scrape the live games. To scrape games that have finished it literally clicks 
on the "download PGN" button on the webpage and gets the PGN that way. As of today this app is semi-crappy. I plan to improve the GUI
somewhere in the future and add more features to it. 

I swear I will kill myself if chess.com changes the HTML structure lol.
