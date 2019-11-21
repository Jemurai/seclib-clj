(ns jemurai.seclib.validator
  (:require [jemurai.seclib.signal :as signal]
            [clojure.spec.alpha :as s])
  (:gen-class))

(defn validate
  [f input]
  (if (f input) true false))

(defn validate-and-signal
  [f input]
  (if (validate f input)
    true
     (signal/security-event input (str f) )))

(defn valid-email?
  "Is the supplied email a valid email address."
  [email]
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

(defn valid-zip?
  [input]
  false
  )

(defn valid-company-name?
  [input]
  false)

(defn valid-guid?
  [input]
  false)

(defn password-complex?
  "Is the password complex?"
  [input]
  false)

(defn have-i-been-pwned?
  "Is the password in a collected breach"
  [input]
  false)

(def short-zip-regex #"^[0-9][0-9][0-9][0-9][0-9]")
(s/def ::short-zip-type (s/and string? #(re-matches short-zip-regex %)))

(defn valid-short-zip?
  "Is the zip short in a collected breach"
  [input]
  (if (s/valid? ::short-zip-type input) true false))
