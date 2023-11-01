alter table book add constraint UK_ehpdfjpu1jm3hijhj4mm0hx9h unique (isbn);
alter table book add constraint FKklnrv3weler2ftkweewlky958 foreign key (author_id) references author;