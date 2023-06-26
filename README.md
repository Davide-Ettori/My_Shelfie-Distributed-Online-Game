# ing-sw-2023-ettori-giammusso-faccincani-gumus

![](https://github.com/Davide-Ettori/ing-sw-2023-ettori-giammusso-faccincani-gumus/blob/main/src/Title%202000x618px.png)

## Software Engineering Project (PoliMi, 2023) - My Shelfie 


### Authors
Polytechnic of Milan - Prof. San Pietro section - Group number 3
- <b> Davide Ettori </b>
- <b> Samuele Giammusso </b>
- <b> Samuele Faccincani </b>
- <b> Furkan Gumus </b>


## Project Specifications
- You can find the project specifications [here](https://github.com/Davide-Ettori/ing-sw-2023-ettori-giammusso-faccincani-gumus/blob/7ed0c08c04f93d05b89c4a760248ac8cd674cad1/deliverables/Project%20Specifications/Requirements.pdf).
- You can find the rulebook of the game [here](https://github.com/Davide-Ettori/ing-sw-2023-ettori-giammusso-faccincani-gumus/blob/7ed0c08c04f93d05b89c4a760248ac8cd674cad1/deliverables/Project%20Specifications/Rulebook.png).

## How to Play
1. Download the App.jar file from deliveries/Jar Files
2. Place it inside a folder of your choice
3. Run the following command: `java -jar App.jar` from a terminal starting at that folder. (you won't be able to double-click to run the app)

The Application will automatically create some helper files inside the folder where the App.jar file is. Don't rename, modify or delete them

If the `java` command does not work, you might need to install java locally from the oracle website  
  
The first window always asks you to enter the IP address to use for connection and the ports for both Socket and RMI (the ports must be different). We suggest using the default ports. The Ip address should be the one of the server for all the players and can be found by the `ipconfig` command on the terminal of the server machine 
Then if you are the first player to connect to this IP and ports you will be assumed to be the server, so the app will ask the number of players and if you want to activate persistence.  
If you are not the first one to connect you will be assumed to be a player, so the app will ask the UI mode, the network mode and if you want to activate resilience.  
You can run multiple games on the same machine and IP if you change the ports for the networks (both Socket and RMI).  
NOTE: if you activate persistence on the server the app won't accept the activation of resilience from the clients (it would make no sense).  
TIP: the enter key on your keyboard always triggers the button for making moves, sending chats and confirming IP.  
Finally, if you use a NON-UNIX terminal the TUI interface won't show the colors

- Here is a video that explains how to play this Board Game: [Tutorial](https://my-shelfie-video.netlify.app)

Remember that in order to play correctly you must connect all the clients and the server to the same network: this means that if you are not playing on local host, you have to connect to the IP address of the network seen from the Computer hosting the Server.   
The best UI experience for the TUI is granted on Unix terminals  
The best UI experience for the GUI is granted on screens of 16 inches or more

## Project Functionalities
In this table you can see which functionalities we implemented

| Functionalities    | Base | Advanced |
|--------------------|------|----------|
| Simplified Rules   | ✅    |          |
| Complete Rules     | ✅    |          |
| Socket             | ✅    |          |
| RMI                | ✅    |          |
| TUI                | ✅    |          |
| GUI                | ✅    |          |
| Multiple Games     |      | ❌        |
| Server Persistence |      | ✅        |
| Client Resilience  |      | ✅        |
| Chat               |      | ✅        |

In this table you can see how the project is evaluated

| Requirements                                       | Grade |
|----------------------------------------------------|-------|
| Simplified Rules + TUI + (Socket o RMI)            | 18    |
| Complete Rules + TUI + (Socket o RMI)              | 20    |
| Complete Rules + TUI + (Socket o RMI) + 1 AF       | 22    |
| Complete Rules + TUI + GUI + (Socket o RMI) + 1 AF | 24    |
| Complete Rules + TUI + GUI + Socket + RMI + 1 AF   | 27    |
| Complete Rules + TUI + GUI + Socket + RMI + 2 AF   | 30    |
| Complete Rules + TUI + GUI + Socket + RMI + 3 AF   | 30L   |

## Documentation
For this project we documented:
### 1. JavaDoc
- [Here](https://javadoc-web.netlify.app) you can find the JavaDoc documentation
### 2. UML
- [Class Diagram](https://github.com/Davide-Ettori/ing-sw-2023-ettori-giammusso-faccincani-gumus/blob/7ed0c08c04f93d05b89c4a760248ac8cd674cad1/deliverables/final/uml/Class%20Diagram.drawio.png) made with Draw.io, it represents the main classes, methods and attributes of the project
- [Full Class Diagram](https://github.com/Davide-Ettori/ing-sw-2023-ettori-giammusso-faccincani-gumus/blob/c3477766b7ebca780c78eec2cef926da89e14e50/deliverables/final/uml/Generated_class_diagram.png) the complete Class Diagram of the project, generated with IntelliJ
- [Sequence Diagram](https://github.com/Davide-Ettori/ing-sw-2023-ettori-giammusso-faccincani-gumus/blob/7ed0c08c04f93d05b89c4a760248ac8cd674cad1/deliverables/final/uml/Sequence%20Diagram.drawio.png) that represent how the messages are exchanged between the Client and the Server
### 3. Test Report 
- [Here](https://test-report-web.netlify.app) you will see our test report.
### 4. Jar File
- [Here](https://github.com/Davide-Ettori/ing-sw-2023-ettori-giammusso-faccincani-gumus/blob/7ed0c08c04f93d05b89c4a760248ac8cd674cad1/deliverables/Jar%20File%20Guide/Jar%20file%20for%20Dummies.pdf) you can find a helpful Guide on how to create the Jar file
 
## Development
The software has been written using **Java 19**.

The IDE used for the development was **IntelliJ Idea Ultimate**.

To create the UML diagrams we chose the app **Draw.io**.

The dependency management was administrated with **Maven** 

The version control was operated with **Git and GitHub**


## License
All rights to My Shelfie game are owned by Cranio Games, which provided the graphical resources to be used for educational purposes only.