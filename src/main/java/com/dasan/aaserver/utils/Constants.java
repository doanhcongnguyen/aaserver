package com.dasan.aaserver.utils;

public interface Constants {

    interface HTTP_CODE {

        int BAD_REQUEST = 400;
    }

    interface PAGINATION {

        int INITIAL_PAGE = 0;
        int INITIAL_PAGE_SIZE = 50;
    }

    interface REGEX {

        String EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    }

    interface AA {

        String ROLE = "role";
        String USER_ID = "userId";
        String FULL_NAME = "fullName";
        String LANGUAGE = "language";
    }

    interface RESOURCE_OWNER {

        interface AUTHORIZED_GRANT_TYPE {

            String PASSWORD = "password";
            String AUTHORIZATION_CODE = "authorization_code";
            String REFRESH_TOKEN = "refresh_token";
        }

        interface SCOPE {

            String READ = "read";
            String WRITE = "write";
        }

        interface TIME {

            int ONE_HOUR = 60 * 60;
            int ONE_DAY = 24 * 60 * 60;
            int ONE_MONTH = 30 * 24 * 60 * 60;
        }
    }
}
