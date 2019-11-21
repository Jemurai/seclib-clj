(ns jemurai.seclib.signal
  (:require [clj-http.client :as client])
  (:gen-class))

;; This is the basic definition of the signal data structure: 
;;     org-key - This allows us to collect signal form different orgs and segment it.
;;     api-key - This allows us to gate signals (throw them away)
;;     source - The system the signal comes from
;;     type - What type of event is it?
;;     timestamp - When the signal was created
;;     detect-ip - The server where the signal was created
;;     remote-ip - The remote server IP that sent the request that created the signal
;;     message - The detail about the signal
;;     tags - To be used to flag different types of things about the signal for indexing and querying
(defrecord Signal [org-key api-key source type timestamp detect-ip remote-ip message tags])

; This only works when the signal service is actually running!
(defn report-signal
  [signal]
  ;(client/post "http://localhost:8000/signal" {:form-params signal :content-type :json})
  )

;; Top level general event reporting methods.
(defn security-event
  "Build the security event."
  [request tags]
  (def signal (->Signal "OrgKey" "APIKey" "seclib" "Security Event" (System/currentTimeMillis) "127.0.0.1" "127.0.0.1" request tags))
  (report-signal signal)
  false)

(defn audit-event
  "Build the audit event."
  [request tags]
  (def signal (->Signal "OrgKey" "APIKey" "seclib" "Audit Event" (System/currentTimeMillis) "127.0.0.1" "127.0.0.1" request tags))
  (report-signal signal)
  false)

(defn exception-event
  "Build the security event."
  [request tags]
  (report-signal
   {:org-key "OrgKey"
    :api-key "APIKey"
    :source "Source"
    :type "Exception"
    :time (System/currentTimeMillis)
    :detect-ip "127.0.0.1"
    :remote-ip "127.0.0.1"
    :request request
    :tags tags} )
  false)

;; Helpers

;; Events that are both security signal and audit related.
(defn login-event
  "Login event handling - which will go to signal and audit
   Parameters required are the request, and the subject that logged in"
  [request name]
  (security-event request name)
  (audit-event request name "Tags related to login controls"))

;; Events that are geared toward generating audit trail.
(defn data-access-event
  "Audit access to data - which will go to audit
   Parameters required are the request, and the subject that logged in"
  [request name data]
  (audit-event request (str name "accessed" data)))

;; Events that are geared toward generating security signal specifically.
(defn unauthorized-event
  "Unauthorized event handling - which will go to signal.
   Parameters required are the request, the subject that logged in
   and the data "
  [request subject action data]
  (security-event request 
                  (str subject " attempted to perform " action " on " data)))

