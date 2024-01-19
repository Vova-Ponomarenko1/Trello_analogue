CREATE TABLE IF NOT EXISTS public.columns
(
    column_id serial PRIMARY KEY,
    column_name varchar(255) NOT NULL,
    position integer NOT NULL,
    board_id integer REFERENCES public.boards(board_id) ON DELETE CASCADE
);
