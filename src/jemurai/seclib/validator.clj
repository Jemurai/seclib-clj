(ns jemurai.seclib.validator
  (:require [jemurai.seclib.signal :as signal])
  (:gen-class))

(defn validate
  [f input]
  (if (f input) true false))

(defn validate-and-signal
  [f input]
  (if (validate f input)
    true
     (signal/security-event input)))

(defn valid-email?
  "Is the supplied email a valid email address."
  [email]
  (signal/security-event "hello")
  (let [pattern #"[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"]
    (if (and (string? email) (re-matches pattern email)) true false)))

(defn valid-number?
  "Is the supplied argument numeric?"
  [input]
    (let [pattern #"[0-9\.]*"]
      (if (and (string? input) (re-matches pattern input)) true false)))

(defn valid-alpha-string?
  "Is the supplied string input just alphabet characters?"
  [input]
  (let [pattern #"[a-z]*"]
    (if (and (string? input) (re-matches pattern input)) true false)))

(defn password-complex?
  "Is the password complex?"
  [input]
  true)

(defn have-i-been-pwned?
  "Is the password in a collected breach"
  [input]
  true)

