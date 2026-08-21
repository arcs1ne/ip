# UI test plan

## Launch command

```text
java '-Dstdout.encoding=UTF-8' -cp C:\Users\tangs\AppData\Local\Temp\tangent-ui-tests Tangent
```

## Test case: add and list all Level 4 task types

- **Aim:** Verify that valid `todo`, `deadline`, and `event` commands create the appropriate task subclasses and that `list` displays their polymorphic string representations.
- **Inputs:**

```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
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
got it! you have a new task: 
[T][ ] borrow book
you now have 1 task!
____________________________________________________________
____________________________________________________________
got it! you have a new task: 
[D][ ] return book (by: Sunday)
you now have 2 tasks!
____________________________________________________________
____________________________________________________________
got it! you have a new task: 
[E][ ] project meeting (from: Mon 2pm to: 4pm)
you now have 3 tasks!
____________________________________________________________
____________________________________________________________
1. [T][ ] borrow book
2. [D][ ] return book (by: Sunday)
3. [E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
bye o/ hope to see you again soon
____________________________________________________________
```

## Test case: reject malformed task commands

- **Aim:** Verify that incomplete task commands, empty descriptions, and an event with reversed markers show an error without adding a task or crashing.
- **Inputs:**

```text
todo
deadline /by Sunday
event meeting /to 4pm /from 2pm
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
please provide a task description!
____________________________________________________________
____________________________________________________________
please use: deadline DESCRIPTION /by TIME
____________________________________________________________
____________________________________________________________
please use: event DESCRIPTION /from START /to END
____________________________________________________________
____________________________________________________________
no tasks yet!
____________________________________________________________
____________________________________________________________
bye o/ hope to see you again soon
____________________________________________________________
```

## Test session record — 2026-08-20

## Attempted test: add and list all Level 4 task types

- **Aim:** Verify that valid `todo`, `deadline`, and `event` commands create the appropriate task subclasses and that `list` displays their polymorphic string representations.
- **Console input sent:**

```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
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
got it! you have a new task: 
[T][ ] borrow book
you now have 1 tasks!
____________________________________________________________
____________________________________________________________
got it! you have a new task: 
[D][ ] return book (by: Sunday)
you now have 2 tasks!
____________________________________________________________
____________________________________________________________
got it! you have a new task: 
[E][ ] project meeting (from: Mon 2pm to: 4pm)
you now have 3 tasks!
____________________________________________________________
____________________________________________________________
1. [T][ ] borrow book
2. [D][ ] return book (by: Sunday)
3. [E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
bye o/ hope to see you again soon
____________________________________________________________
```

- **Actual output:**

```text
____________________________________________________________
????????? ?????? ????   ??? ??????? ????????????   ????????????
??????????????????????  ??????????? ?????????????  ????????????
   ???   ?????????????? ??????  ??????????  ?????? ???   ???
   ???   ?????????????????????   ?????????  ??????????   ???
   ???   ???  ?????? ?????????????????????????? ??????   ???
   ???   ???  ??????  ????? ??????? ???????????  ?????   ???
good morning/afternoon/evening ^-^ I'm TANGENT.
what do you want me to do?
____________________________________________________________
____________________________________________________________
got it! you have a new task: 
[T][ ] borrow book
you now have 1 tasks!
____________________________________________________________
____________________________________________________________
got it! you have a new task: 
[D][ ] return book (by: Sunday)
you now have 2 tasks!
____________________________________________________________
____________________________________________________________
got it! you have a new task: 
[E][ ] project meeting (from: Mon 2pm to: 4pm)
you now have 3 tasks!
____________________________________________________________
____________________________________________________________
1. [T][ ] borrow book
2. [D][ ] return book (by: Sunday)
3. [E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
bye o/ hope to see you again soon
____________________________________________________________
```

- **Result:** FAIL — the banner was rendered as question marks; all Level 4 task output otherwise matched the expected text.

## Test session record — 2026-08-21

## Attempted test: add and list all Level 4 task types

- **Aim:** Verify valid creation and listing of all Level 4 task types.
- **Console input sent:**

```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
list
bye
```

- **Expected output:**

```text
Matches the complete expected-output block for the test case above.
```

- **Actual output:**

```text
Matches the complete expected-output block for the test case above.
```

- **Result:** PASS

## Attempted test: reject malformed task commands

- **Aim:** Verify malformed task commands are rejected without adding a task or crashing.
- **Console input sent:**

```text
todo
deadline /by Sunday
event meeting /to 4pm /from 2pm
list
bye
```

- **Expected output:**

```text
Matches the complete expected-output block for the test case above.
```

- **Actual output:**

```text
Matches the complete expected-output block for the test case above.
```

- **Result:** PASS

## Test session record — 2026-08-21 (custom-exception refactor review)

## Attempted test: add and list all Level 4 task types

- **Aim:** Verify that valid `todo`, `deadline`, and `event` commands create the appropriate task subclasses and that `list` displays their polymorphic string representations.
- **Console input sent:**

```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
list
bye
```

- **Expected output:**

```text
Matches the complete expected-output block for the test case above.
```

- **Actual output:**

```text
Matches the complete expected-output block except the first task count line is:
you now have 1 task!
```

- **Result:** FAIL — the test plan expects `you now have 1 tasks!`, but the program correctly prints `you now have 1 task!`. Testing stopped after this first failure as required.
