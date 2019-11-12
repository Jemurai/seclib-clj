(ns jemurai.seclib.validator
  (:gen-class)
  )

(defn valid-email?
  "Is the supplied email a valid email address."
  [email]
  (let [pattern #"[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"]
    (if (and (string? email) (re-matches pattern email)) true false)
  ))


;; These are all still in progress.

(defn valid-number?
  "Is the supplied argument numeric?"
  [input]
  true
  )

(defn valid-alpha-string?
  "Is the supplied string input just alphabet characters?"
  [input]
  true
  )

(defn password-complex?
  "Is the password complex?"
  [input]
  true
  )

(defn have-i-been-pwned?
  "Is the password in a collected breach"
  [input]
  true
  )

