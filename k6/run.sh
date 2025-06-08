#!/usr/bin/env bash

k6 run scenarios/email_signup.js \
--http-debug="full" \
-e URL=http://localhost:8080