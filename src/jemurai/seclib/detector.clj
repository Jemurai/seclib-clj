(ns jemurai.seclib.detector
  (:require [jemurai.seclib.signal :as signal])
  (:gen-class))

(defn detect
  [f input]
  (if (f input) true false))

(defn detect-and-signal
  [f input]
  (if (detect f input)
    true
    (signal/security-event input (str f))))

(defn looks-like-xss?
  "Is the supplied string xss?"
  [input]
  (if
   (or
    (if (re-find #"<script>" input) true false)
    (if (re-find #"alert\(" input) true false)
    (if (re-find #"document.cookie" input) true false)
    (if (re-find #"document.write" input) true false)) true false))

(defn looks-like-sqli?
  "Is the supplied string sqli?"
  [input]
  (if
   (or
    (if (re-find #"--;" input) true false)
    (if (re-find #"drop table" input) true false)
    (if (re-find #"or 1=1" input) true false)
    (if (re-find #"or \"1\"=\"1\"" input) true false)) true false))

(defn detect-on-string
  [input]
  (if (or
       (looks-like-xss? input)
       (looks-like-sqli? input))
    true false))
