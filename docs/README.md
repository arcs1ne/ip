# Tangent User Guide

Tangent is chatbot for managing tasks, deadlines, and events.

Enter commands in chatbot window. Changes save automatically.

Task numbers start at `1`. Use spaces for separate tasks + `-` for inclusive ranges, such as `mark 1 3-5`.
Use date format `d/M/yyyy HHmm`, such as `25/9/2026 2359`.

## `todo`

Adds task without date or time.

Example: `todo read chapter 5`

## `deadline`

Adds task with deadline.

Example: `deadline submit report /by 25/9/2026 2359`

## `event`

Adds task with start + end time.

Example: `event team meeting /from 26/9/2026 1400 /to 26/9/2026 1500`

## `list`

Shows all tasks with task numbers.

Example: `list`

## `find`

Shows tasks whose descriptions contain keyword, case-insensitive.

Example: `find report`

## `mark`

Marks one or more tasks as done.

Example: `mark 1 3-5`

## `unmark`

Marks one or more tasks as undone.

Example: `unmark 2`

## `delete`

Deletes one or more tasks permanently.

Example: `delete 1 3-5`

## `bye`

Closes Tangent.

Example: `bye`
