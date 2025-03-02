# Caviar User Guide

## Introduction
> Caviar, a type of fish roe. Speak to the fish in the water for your tasks!

Caviar is an interactive task manager with a personality—sometimes ending and starting sentences with _roe_.

Choose between CLI or GUI interaction with your task manager!

## Features
- **Tasks Management** – Add, mark, unmark, list, and delete tasks.
- **Deadline Tracker** – Set deadlines with a due date and time.
- **Event Scheduler** – Schedule events with start and end dates and times.
- **Find Tasks** – Search for tasks by description.
- **Sort Task List** – Sort your list with different options (chronologically or in reverse order).
- **Error Handler** – Get warnings if commands are invalid.

---

## Setup
1. Download the latest release in GitHub Open, and run the application (e.g., `java -jar Caviar.jar`).
2. If you prefer the GUI, launch with gradle (e.g., `./gradlew run`) normally and interact with the chat window.  
   For CLI usage, type commands into your terminal after running the `.jar` file.

---
## Interactions

### 1. Adding a To-Do Task
Creates a simple task with no date/time.

**Command:**
```plaintext
todo <task description>
```

### **2. Adding a Deadline**

**Command:**
```plaintext
deadline <task description> /by <YYYY-MM-DD HHmm>
```
*Compulsory:* description and datetime

### **3. Adding an Event**

**Command:**
```plaintext
event <event description> /from <YYYY-MM-DD HHmm> /to <YYYY-MM-DD HHmm>
```
*Compulsory:* description, /from datetime, and /to datetime

### **4. View Task List**

**Command:**
```plaintext
list
```

### **5. Find Tasks**

**Command:**
```plaintext
find <keyword>
```
*Compulsory:* tasks contain keyword in their description.

### **6. Sort Tasks**

**Commands:**
```plaintext
sort 1
```
```plaintext
sort 2
```
* Sort all tasks chronologically _or_ reverse-chronologically

```plaintext
sort todo 1
```
* Sort only todo tasks chronologically

```plaintext
sort deadline 2
```
* Sort only deadline tasks reverse-chronologically

```plaintext
sort event 1
```
* Sort only event tasks chronologically


### **7. Mark Task as Done**
**Command:**
```plaintext
mark <task number>
```

### **8. Unmark a Task**

**Command:**
```plaintext
unmark <task number>
```

### **9. Delete Task**

**Command:**
```plaintext
delete <task number>
```
*Compulsory:* Deleting tasks by indices.

### **11. Exiting Caviar**

**Command:**
```plaintext
bye
```

Willing to contribute to this project? feel free to submit PRs! 

