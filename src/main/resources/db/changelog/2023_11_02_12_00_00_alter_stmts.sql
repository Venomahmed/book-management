alter table book add constraint UK_ehpdfjpu1jm3hijhj4mm0hx9h unique (isbn);
alter table book add constraint UK_91qrec84hfmfewqwj12p2nsyn unique (publication_date);
alter table book add constraint UK_g0286ag1dlt4473st1ugemd0m unique (title);
alter table book add constraint FKklnrv3weler2ftkweewlky958 foreign key (author_id) references author;