# ing-sw-2023-ettori-giammusso-faccincani-gumus

![](https://github.com/Davide-Ettori/ing-sw-2023-ettori-giammusso-faccincani-gumus/blob/main/deliveries/UML%20Diagrams/PNG/Title%202000x618px.png)

## Software Engineering Project (PoliMi, 2023) - My Shelfie 


### Authors
Polytechnic of Milan - Prof. San Pietro section - Group number 3
- <b> Davide Ettori </b>
- <b> Samuele Giammusso </b>
- <b> Samuele Faccincani </b>
- <b> Furkan Gumus </b>


## Project Specifications
- You can find the project specifications [here](https://github.com/Davide-Ettori/ing-sw-2023-ettori-giammusso-faccincani-gumus/blob/main/deliveries/Project%20Specifications/Requirements.pdf).
- You can find the rulebook of the game [here](https://github.com/Davide-Ettori/ing-sw-2023-ettori-giammusso-faccincani-gumus/blob/main/deliveries/Project%20Specifications/Rulebook.png).

## How to Play
1. Clone or download the repository
2. Open the root folder of the project
3. For MacOS, Linux and Windows Poweshell: run the following command `cd "deliveries/Jar Files" ; java -cp App.jar it.polimi.ingsw.App` from there

If you want to use the Windows CMD run `cd "deliveries/Jar Files" && java -cp App.jar it.polimi.ingsw.App` instead

If the `java` command does not work, you might need to install java locally from the oracle website  
  
The first window always asks you to enter the IP address to use for connection and the ports for both Socket and RMI.  
Then if you are the first player to connect to this IP and ports you will be assumed to be the server, so the app will ask the number of players and if you want to activate persistence.  
If you are not the first one to connect you will be assumed to be a player, so the app will ask the UI mode, the network mode and if you want to activate resilience.  
You can run multiple games on the same machine and IP if you change the ports for the networks (both Socket and RMI).  
NOTE: if you activate persistence on the server the app won't accept the activation of resilience from the clients (it would make no sense).  
TIP: the enter key on your keyboard always triggers the button for making moves, sending chats and confirming IP.  

- Here is a video that explains how to play this Board Game: [Tutorial](https://my-shelfie-video.netlify.app)

Remember that in order to play correctly you must connect all the clients and the server to the same network

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
- [Class Diagram](https://github.com/Davide-Ettori/ing-sw-2023-ettori-giammusso-faccincani-gumus/blob/54cfcebd3fc741955e5c213090cbb66ec0a11f60/deliveries/UML%20Diagrams/PNG/Class%20Diagram.drawio.png) made with Draw.io, it represents the main classes, methods and attributes of the project
- [Sequence Diagram](https://github.com/Davide-Ettori/ing-sw-2023-ettori-giammusso-faccincani-gumus/blob/25beff41190c2fd519979629af4a3104baf94605/deliveries/UML%20Diagrams/PNG/Sequence%20Diagram.drawio.png) that represent how the messages are exchanged between the Client and the Server
- [State Diagram](https://github.com/Davide-Ettori/ing-sw-2023-ettori-giammusso-faccincani-gumus/blob/main/deliveries/UML%20Diagrams/PNG/State%20Diagram.drawio.png) that describe the general behaviour of the system
- [Full Class Diagram](https://github.com/Davide-Ettori/ing-sw-2023-ettori-giammusso-faccincani-gumus/blob/main/deliveries/UML%20Diagrams/PNG/Full%20Class%20Diagram.drawio.png) the complete Class Diagram of the project, generated with IntelliJ
### 3. Test Report 
- [Here](https://test-report-web.netlify.app) you will see our test report.

## Development
The software has been written using **Java 19**.

The IDE used for the development was **IntelliJ Idea Ultimate**.

To create the UML diagrams we chose the app **Draw.io**.

The dependency management was administrated with **Maven** 

The version control was operated with **Git and GitHub**


## License
All rights to My Shelfie game are owned by Cranio Games, which provided the graphical resources to be used for educational purposes only.