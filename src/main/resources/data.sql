INSERT INTO vote_user (id, name, is_admin)
    VALUES ('10', 'Lars', false);

INSERT INTO vote_user (id, name, is_admin)
    VALUES ('20', 'Tim', false);

INSERT INTO vote_user (id, name, is_admin)
    VALUES ('30', 'Harald', false);

INSERT INTO poll (id, pincode, question, answera, answerb, is_private, start_date, end_date, is_closed, user_id)
    VALUES ('100', 123456, 'Ananas på pizza?', 'Nei', 'Absolutt ikke!', 0, {ts '2022-10-10 18:47:52.69'}, {ts '2022-10-30 18:47:52.69'}, 0, '10');

INSERT INTO vote (id, answer, poll_id, user_id)
    VALUES ('1000', 0, '100', '20');