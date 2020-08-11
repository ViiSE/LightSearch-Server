-- From DB connection test
CREATE USER IF NOT EXISTS ls_test PASSWORD 'test' ADMIN;

-- From Authorization test
INSERT INTO LS_RESPONSE(LSCODE, DDOC, CMDOUT, STATE)
    VALUES (1, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss.s'), '{"is_done":"true","message":"Test auth!","user_ident":"777", "tk_list":["TK 1", "TK 2"],"sklad_list":["Sklad 1", "Sklad 2", "Sklad 3"]}', false);

-- From Search test
INSERT INTO LS_RESPONSE(LSCODE, DDOC, CMDOUT, STATE)
    VALUES (2, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss.s'), '{"is_done":"true","data":[{"subdiv":"Sklad 1", "id":"22505", "name":"Item 1", "amount":"10", "price":"200", "ei":"pcs."}]}', false);

-- From OpenSoftCheck test
INSERT INTO LS_RESPONSE(LSCODE, DDOC, CMDOUT, STATE)
    VALUES (3, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss.s'), '{"is_done":"true","message":"TEST! SoftCheck is opened!"}', false);

-- From CancelSoftCheck test
INSERT INTO LS_RESPONSE(LSCODE, DDOC, CMDOUT, STATE)
    VALUES (4, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss.s'), '{"is_done":"true","message":"TEST! SoftCheck is canceled!"}', false);

-- From CloseSoftCheck test
INSERT INTO LS_RESPONSE(LSCODE, DDOC, CMDOUT, STATE)
    VALUES (5, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss.s'), '{"is_done":"true","message":"TEST! SoftCheck is closed!"}', false);

-- From ConfirmSoftCheckProducts test
INSERT INTO LS_RESPONSE(LSCODE, DDOC, CMDOUT, STATE)
    VALUES (6, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss.s'), '{"is_done":"true","data":[{"id":"22505","amount":"7"}, {"id":"225051","amount":"70"}]}', false);