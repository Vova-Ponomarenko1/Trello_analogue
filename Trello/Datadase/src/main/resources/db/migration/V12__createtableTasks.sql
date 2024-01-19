CREATE TABLE IF NOT EXISTS public.tasks
(
    task_id serial PRIMARY KEY,
    column_id integer REFERENCES public.columns(column_id) ON DELETE NO ACTION,
    task_name varchar(255) NOT NULL,
    task_description text,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    position integer NOT NULL,
    board_id integer REFERENCES public.boards(board_id) ON DELETE CASCADE
);
