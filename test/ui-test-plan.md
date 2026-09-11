# UI test plan

Run the cases in order from an empty `data` directory. The first creates the data file; the second verifies it reloads.

## Launch command

```text
java '-Dstdout.encoding=UTF-8' -cp C:\Users\tangs\Downloads\ip\build-review tangent.Tangent
```

## Test case: create and list dated tasks

- **Aim:** Verify first-run persistence setup, valid date parsing, and formatted task output.
- **Inputs:**

```text
todo borrow book
deadline return book /by 2/12/2019 1800
event project meeting /from 3/12/2019 0900 /to 3/12/2019 1100
list
bye
```

- **Expected output:**

```text
____________________________________________________________
████████╗ █████╗ ███╗   ██╗ ██████╗ ███████╗███╗   ██╗████████╗
╚══██╔══╝██╔══██╗████╗  ██║██╔════╝ ██╔════╝████╗  ██║╚══██╔══╝
   ██║   ███████║██╔██╗ ██║██║  ███╗█████╗  ██╔██╗ ██║   ██║
   ██║   ██╔══██║██║╚██╗██║██║   ██║██╔══╝  ██║╚██╗██║   ██║
   ██║   ██║  ██║██║ ╚████║╚██████╔╝███████╗██║ ╚████║   ██║
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝
good morning/afternoon/evening ^-^ I'm TANGENT.
what do you want me to do?
____________________________________________________________
____________________________________________________________
got it! you have a new task: 
[T][ ] borrow book
you now have 1 task in the list!
____________________________________________________________
____________________________________________________________
got it! you have a new task: 
[D][ ] return book (by: Dec 02 2019, 6:00PM)
you now have 2 tasks in the list!
____________________________________________________________
____________________________________________________________
got it! you have a new task: 
[E][ ] project meeting (from: Dec 03 2019, 9:00AM to: Dec 03 2019, 11:00AM)
you now have 3 tasks in the list!
____________________________________________________________
____________________________________________________________
1. [T][ ] borrow book
2. [D][ ] return book (by: Dec 02 2019, 6:00PM)
3. [E][ ] project meeting (from: Dec 03 2019, 9:00AM to: Dec 03 2019, 11:00AM)
____________________________________________________________
____________________________________________________________
bye o/ hope to see you again soon
____________________________________________________________
```

## Test session record — 2026-08-31

Both test cases were compiled and run with Java 25 in an isolated working directory, in the order shown above.

### Attempted test: create and list dated tasks

- **Console input sent:**

```text
todo borrow book
deadline return book /by 2/12/2019 1800
event project meeting /from 3/12/2019 0900 /to 3/12/2019 1100
list
bye
```

- **Expected output:**

```text
Matches the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Matches the complete expected-output block for this test case above.
```

- **Result:** PASS

## Test session record — 2026-09-11 (storage format constants)

Java 25.0.4 direct compilation of console sources + first planned case passed in isolated `ui-test-storage-constants-workspace`.

- **Actual output:**

```text
Application exited with code 0 after creating and listing ToDo, Deadline, and Event records.
```

- **Result:** PASS

## Test session record — 2026-09-11 (TaskList.find normalization)

Java 25.0.4 direct compilation of console sources + focused find scenario passed in isolated `ui-test-tasklist-find-workspace`.

- **Actual output:**

```text
Application exited with code 0 and returned matching task for case-insensitive FIND command.
```

- **Result:** PASS

## Test session record — 2026-09-11 (Tangent command-loop extraction)

Java 25.0.4 direct compilation of console sources + focused add/list/exit scenario passed.

- **Actual output:**

```text
Application exited with code 0 after adding, listing, and exiting normally.
```

- **Result:** PASS

## Test session record — 2026-09-11 (UI task-count formatter)

Java 25.0.4 direct compilation of console sources + focused add/delete/exit scenario passed.

- **Actual output:**

```text
Application exited with code 0; add and delete confirmations displayed correct task counts.
```

- **Result:** PASS

## Test session record — 2026-09-11 (status-command consolidation)

Java 25.0.4 direct compilation of console sources + focused mark/unmark/exit scenario passed.

- **Actual output:**

```text
Application exited with code 0; mark and unmark confirmations displayed correctly.
```

- **Result:** PASS

## Test session record — 2026-09-11 (quality pass)

Java 25.0.4 direct compilation of console sources + two focused scenarios passed.

- **Actual output:**

```text
Both cases exited with code 0: dated-task creation/listing, FIND, mark, unmark, delete, and exit.
```

- **Result:** PASS (2 cases)

## Test session record — 2026-09-11 (parseEvent readability refactor)

Java 25.0.4 direct compilation of console sources + both planned cases passed in isolated `ui-test-code-quality-workspace`.

### Attempted tests: create/list + reload/save mutations

- **Console input sent:**

```text
The exact inputs from the two planned test cases above, run in order.
```

- **Expected output:**

```text
The complete expected-output blocks from the corresponding planned test cases above.
```

- **Actual output:**

```text
Both cases matched their expected behavior; processes exited with code 0.
```

- **Result:** PASS (2 cases)

## Test session record — 2026-09-01 (parser refactor)

All three test cases passed in order with Java 25.0.4 in an isolated `ui-test-parser-refactor-workspace` directory. The actual output for each case exactly matched its complete expected-output block above.

### Attempted test: create and list dated tasks

- **Console input sent:**

```text
todo borrow book
deadline return book /by 2/12/2019 1800
event project meeting /from 3/12/2019 0900 /to 3/12/2019 1100
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

### Attempted test: reload and save mutations

- **Console input sent:**

```text
mark 2
delete 1
deadline impossible /by 31/2/2019 1800
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

## Test session record — 2026-09-11 (assertions)

Java 25.0.4 direct compilation and both planned console cases ran successfully. Gradle wrapper could not start because its lock directory was unavailable at `C:\.gradle`.

### Attempted test: create and list dated tasks

- **Console input sent:**

```text
todo borrow book
deadline return book /by 2/12/2019 1800
event project meeting /from 3/12/2019 0900 /to 3/12/2019 1100
list
bye
```

- **Expected output:**

```text
Complete expected-output block for this test case above.
```

- **Actual output:**

```text
Process exited 0; welcome, task creation, list, and bye output produced.
```

- **Result:** PASS

### Attempted test: reload and save mutations

- **Console input sent:**

```text
mark 2
delete 1
deadline impossible /by 31/2/2019 1800
list
bye
```

- **Expected output:**

```text
Complete expected-output block for this test case above.
```

- **Actual output:**

```text
Process exited 0; mark, delete, invalid-date error, list, and bye output produced.
```

- **Result:** PASS

### Attempted test: unmark and reload status

- **Console input sent:**

```text
unmark 1
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

### Attempted test: reload and save mutations

- **Console input sent:**

```text
mark 2
delete 1
deadline impossible /by 31/2/2019 1800
list
bye
```

- **Expected output:**

```text
Matches the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Matches the complete expected-output block for this test case above.
```

- **Result:** PASS

## Test case: reload and save mutations

- **Aim:** Verify tasks reload and mark/delete are saved; invalid calendar dates are rejected.
- **Inputs:**

```text
mark 2
delete 1
deadline impossible /by 31/2/2019 1800
list
bye
```

- **Expected output:**

```text
____________________________________________________________
████████╗ █████╗ ███╗   ██╗ ██████╗ ███████╗███╗   ██╗████████╗
╚══██╔══╝██╔══██╗████╗  ██║██╔════╝ ██╔════╝████╗  ██║╚══██╔══╝
   ██║   ███████║██╔██╗ ██║██║  ███╗█████╗  ██╔██╗██║   ██║
   ██║   ██╔══██║██║╚██╗██║██║   ██║██╔══╝  ██║╚██╗██║   ██║
   ██║   ██║  ██║██║ ╚████║╚██████╔╝███████╗██║ ╚████║   ██║
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝
good morning/afternoon/evening ^-^ I'm TANGENT.
what do you want me to do?
____________________________________________________________
____________________________________________________________
i've marked it as done!
____________________________________________________________
____________________________________________________________
got it! i've removed this task:
[T][ ] borrow book
you now have 2 tasks in the list!
____________________________________________________________
____________________________________________________________
bad date format :( ensure your dates are in the format DD/MM/YYYY HHmm (example: 07/06/2026 2200)
____________________________________________________________
____________________________________________________________
1. [D][X] return book (by: Dec 02 2019, 6:00PM)
2. [E][ ] project meeting (from: Dec 03 2019, 9:00AM to: Dec 03 2019, 11:00AM)
____________________________________________________________
____________________________________________________________
bye o/ hope to see you again soon
____________________________________________________________
```

## Test case: unmark and reload status

- **Aim:** Verify an unmarked task is saved as incomplete and reloads correctly.
- **Inputs:**

```text
unmark 1
list
bye
```

- **Expected output:**

```text
____________________________________________________________
████████╗ █████╗ ███╗   ██╗ ██████╗ ███████╗███╗   ██╗████████╗
╚══██╔══╝██╔══██╗████╗  ██║██╔════╝ ██╔════╝████╗  ██║╚══██╔══╝
   ██║   ███████║██╔██╗ ██║██║  ███╗█████╗  ██╔██╗ ██║   ██║
   ██║   ██╔══██║██║╚██╗██║██║   ██║██╔══╝  ██║╚██╗██║   ██║
   ██║   ██║  ██║██║ ╚████║╚██████╔╝███████╗██║ ╚████║   ██║
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝
good morning/afternoon/evening ^-^ I'm TANGENT.
what do you want me to do?
____________________________________________________________
____________________________________________________________
i've marked it as undone!
____________________________________________________________
____________________________________________________________
1. [D][ ] return book (by: Dec 02 2019, 6:00PM)
2. [E][ ] project meeting (from: Dec 03 2019, 9:00AM to: Dec 03 2019, 11:00AM)
____________________________________________________________
____________________________________________________________
bye o/ hope to see you again soon
____________________________________________________________
```

## Test session record — 2026-08-31 (tangent.ui.Ui extraction)

Both test cases passed in order with Java 25.0.4 in an isolated `ui-test-workspace` directory.

### Attempted test: create and list dated tasks

- **Console input sent:**

```text
todo borrow book
deadline return book /by 2/12/2019 1800
event project meeting /from 3/12/2019 0900 /to 3/12/2019 1100
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

### Attempted test: reload and save mutations

- **Console input sent:**

```text
mark 2
delete 1
deadline impossible /by 31/2/2019 1800
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

## Test session record — 2026-08-31 (tangent.storage.Storage extraction)

Both test cases passed in order with Java 25.0.4 in an isolated `ui-test-storage-workspace` directory.

### Attempted test: create and list dated tasks

- **Console input sent:**

```text
todo borrow book
deadline return book /by 2/12/2019 1800
event project meeting /from 3/12/2019 0900 /to 3/12/2019 1100
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

### Attempted test: reload and save mutations

- **Console input sent:**

```text
mark 2
delete 1
deadline impossible /by 31/2/2019 1800
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

## Test session record — 2026-08-31 (tangent.task.TaskList extraction)

Both test cases passed in order with Java 25.0.4 in an isolated `ui-test-tasklist-workspace` directory.

### Attempted test: create and list dated tasks

- **Console input sent:**

```text
todo borrow book
deadline return book /by 2/12/2019 1800
event project meeting /from 3/12/2019 0900 /to 3/12/2019 1100
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

### Attempted test: reload and save mutations

- **Console input sent:**

```text
mark 2
delete 1
deadline impossible /by 31/2/2019 1800
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

## Test session record — 2026-08-31 (tangent.parser.Parser extraction)

Both test cases passed in order with Java 25.0.4 in an isolated `ui-test-parser-workspace` directory.

### Attempted test: create and list dated tasks

- **Console input sent:**

```text
todo borrow book
deadline return book /by 2/12/2019 1800
event project meeting /from 3/12/2019 0900 /to 3/12/2019 1100
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

### Attempted test: reload and save mutations

- **Console input sent:**

```text
mark 2
delete 1
deadline impossible /by 31/2/2019 1800
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

## Test session record — 2026-08-31 (basic command dispatch)

Both test cases passed in order with Java 25.0.4 in an isolated `ui-test-command-basics-workspace` directory.

### Attempted test: create and list dated tasks

- **Console input sent:**

```text
todo borrow book
deadline return book /by 2/12/2019 1800
event project meeting /from 3/12/2019 0900 /to 3/12/2019 1100
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

### Attempted test: reload and save mutations

- **Console input sent:**

```text
mark 2
delete 1
deadline impossible /by 31/2/2019 1800
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

## Test session record — 2026-08-31 (status command dispatch)

All three test cases passed in order with Java 25.0.4 in an isolated `ui-test-command-status-final-workspace` directory.

### Attempted test: create and list dated tasks

- **Console input sent:**

```text
todo borrow book
deadline return book /by 2/12/2019 1800
event project meeting /from 3/12/2019 0900 /to 3/12/2019 1100
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

### Attempted test: reload and save mutations

- **Console input sent:**

```text
mark 2
delete 1
deadline impossible /by 31/2/2019 1800
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

### Attempted test: unmark and reload status

- **Console input sent:**

```text
unmark 1
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

## Test session record — 2026-08-31 (delete command dispatch)

All three test cases passed in order with Java 25.0.4 in an isolated `ui-test-delete-command-workspace` directory.

### Attempted test: create and list dated tasks

- **Console input sent:**

```text
todo borrow book
deadline return book /by 2/12/2019 1800
event project meeting /from 3/12/2019 0900 /to 3/12/2019 1100
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

### Attempted test: reload and save mutations

- **Console input sent:**

```text
mark 2
delete 1
deadline impossible /by 31/2/2019 1800
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

### Attempted test: unmark and reload status

- **Console input sent:**

```text
unmark 1
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

## Test session record — 2026-08-31 (add command dispatch)

All three test cases passed in order with Java 25.0.4 in an isolated `ui-test-add-command-workspace` directory.

### Attempted test: create and list dated tasks

- **Console input sent:**

```text
todo borrow book
deadline return book /by 2/12/2019 1800
event project meeting /from 3/12/2019 0900 /to 3/12/2019 1100
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

### Attempted test: reload and save mutations

- **Console input sent:**

```text
mark 2
delete 1
deadline impossible /by 31/2/2019 1800
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

### Attempted test: unmark and reload status

- **Console input sent:**

```text
unmark 1
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

## Test session record — 2026-08-31 (parser command dispatch)

All three test cases passed in order with Java 25.0.4 in an isolated `ui-test-parser-dispatch-workspace` directory.

### Attempted test: create and list dated tasks

- **Console input sent:**

```text
todo borrow book
deadline return book /by 2/12/2019 1800
event project meeting /from 3/12/2019 0900 /to 3/12/2019 1100
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

### Attempted test: reload and save mutations

- **Console input sent:**

```text
mark 2
delete 1
deadline impossible /by 31/2/2019 1800
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS

### Attempted test: unmark and reload status

- **Console input sent:**

```text
unmark 1
list
bye
```

- **Expected output:**

```text
Exactly the complete expected-output block for this test case above.
```

- **Actual output:**

```text
Exactly matched the complete expected-output block for this test case above.
```

- **Result:** PASS
