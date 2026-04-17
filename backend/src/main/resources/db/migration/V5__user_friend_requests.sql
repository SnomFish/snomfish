CREATE TABLE user_friend_requests (
    user_id BIGINT NOT NULL,
    request_id BIGINT NOT NULL,

    PRIMARY KEY (user_id, request_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (request_id) REFERENCES users(id)
);