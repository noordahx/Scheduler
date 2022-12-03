<!--# My Personal Project-->
# **Study room scheduler app**

## What will the application do?
* The application will help people schedule and book study rooms at the UBC ICCS building. Users can create, reschedule, delete their booking slots using this app.

## Who will use it?
* UBC students and staff, in general, can use this app to book study places at the ICCS building.

## Why is this project of interest to you?
* I am interested in this project because I want to learn more about making scheduling apps and would like to implement the project in my student residence.

## User Stories
* As a *user*, I want to be able to *list* all the available timeslots.
* As a *user*, I want to be able to *add* my own booking study place to one of the available timeslots. 
* As a *user*, I want to be able to *reschedule* the booking at the specified time.
* As a *user*, I want to be able to *delete* the booking. 
* As a *user*, I want my program to automatically save Schedule when I close the program
* As a *user*, I want my program to automatically load Schedule when I open the program

## Instructions for Grader
* You can book an event by adding new Booking Timeslot to a Room Time table
* You can delete an event by clicking delete button in Delete menu
* You can locate my visual component in Main menu
* You can save the state of my application by navigating to the menu and clicking File > Load 
* You can save the state of my application by exiting the application and save prompt will be shown

## Phase 4

### Phase 4: Task 2

<details>
    <summary> Logs </summary>

```
Fri Dec 02 20:00:32 PST 2022
Created new StudyRoom class with the name X100
Fri Dec 02 20:00:32 PST 2022
Added timeslots to X100
Fri Dec 02 20:00:32 PST 2022
Created new StudyRoom class with the name X200
Fri Dec 02 20:00:32 PST 2022
Added timeslots to X200
Fri Dec 02 20:00:32 PST 2022
Created new StudyRoom class with the name X300
Fri Dec 02 20:00:32 PST 2022
Added timeslots to X300
Fri Dec 02 20:00:32 PST 2022
Created new StudyRoom class with the name X400
Fri Dec 02 20:00:32 PST 2022
Added timeslots to X400
Fri Dec 02 20:00:32 PST 2022
Created new ListRooms object
Fri Dec 02 20:00:39 PST 2022
Timeslot successfully added!
Fri Dec 02 20:00:39 PST 2022
Booked a timeslot for Nurda at 9
Fri Dec 02 20:00:45 PST 2022
Deleted an old booking
Fri Dec 02 20:00:45 PST 2022
Deleted timeslot at 9
Fri Dec 02 20:00:56 PST 2022
Timeslot successfully added!
Fri Dec 02 20:00:56 PST 2022
Booked a timeslot for Nurda at 9
Fri Dec 02 20:01:04 PST 2022
Deleted an old booking
Fri Dec 02 20:01:04 PST 2022
Deleted timeslot at 9
Fri Dec 02 20:01:04 PST 2022
Timeslot successfully added!
Fri Dec 02 20:01:04 PST 2022
Booked a timeslot for  at 14
Fri Dec 02 20:01:24 PST 2022
Saved X100 as JSON
Fri Dec 02 20:01:24 PST 2022
Saved X200 as JSON
Fri Dec 02 20:01:24 PST 2022
Saved X300 as JSON
Fri Dec 02 20:01:24 PST 2022
Saved X400 as JSON
Fri Dec 02 20:01:24 PST 2022
Saving List Rooms object in Json format ...
```

The first ~20 lines of eventlog are related to initialization of the program where 1 ListRooms class initializes and adds 4 different rooms (X100 - X400). For each room, StudyRoom class initializes and adds 9 TimeSlot objects to its timeslots ArrayList.
</details>

### Phase 4: Task 3

My program could be modified and upgraded by adding abstract class for the UI. Most of the classes at `main/ui/frames` share the same behavior such as same layouts, same menu lists and same pop-up windows. By adding an abstract UI class that has all the abovementioned shared behavior and some unique ones as abstract methods, I would reduce the code repetition in my project. Additionally, I would add obersever pattern in my project to notify other class about changes in `TimeSlot`, instead of iterating through the `TimeSlot` objects List everytime. 

<!-- A subtitle

A *bulleted* list:
- item 1
- item 2
- item 3
- item 4
- item 5
An example of text with **bold** and *italic* fonts.  
-->
