# Instructor Project Meeting Minutes

**Time**: Week 6

**Attendees**: 
- Isa Gonzalez
- Brennan Benson
- Jason Lu

**Late Attendees**: 

**Note Taker**: Yiji Zhang


## Agenda

### Progress Evaluation
progress

### Questions from the team (about design, linter, tests, etc.)
1. One big branch strategy was to avoid merge conflict

Answer: 
Say Class A developer needs to use a method defined in Class B.
In this case, Class A developer needs to communicate with Class B 
developer and determines the method signatures
and the specification for the method first, t
hen Class A developer will use mocking and create dummy boilerplate Class B
when developing class A.

Each person should create their own feature branch.  
Merge conflict is inevitable but hopefully is a straightforward case.


2. Is it okay to push "empty" player class

Answer: In each person's feature branch, yes. 
But it should never be pushed to main without any test cases that need them.

3. git merge main

Answer: The best practice to efficiently solve merge conflict is to merge main often into your feature branch.
Suppose your feature branch's name is wip-card. Then the commands to do are:
```
git checkout wip-card
git pull origin main
git merge main
```

5. abstract class testing and BVA

Answer: For the concrete methods in the abstract class, you may begin by thinking of the general contract for this method. Then you can look at each subclass and see whether it introduces new test cases.

6. one expansion pack instead of 2

Note: This is an adjustment to the team of 3.

7. UI: no GUI is required.

Note: You are encouraged to implement any GUI (Java swing or OpenFX are commonly used Java GUI libraries). A command line console interface, however, suffices for this project.

8. The need for "Factory" class

Note: The idea is that there should be classes dedicated for instantiating objects. 
We should avoid creating objects in domain layer classes.

9. review design. Particularly, draw pile and card pile.

Note: Please see docs/design/design.puml. The plugin needed to render the diagram is called PlantUML.

### Teamwork
#### Strength: What has been working well
Things are working well.

#### Concerns: What can be improved
N/A
