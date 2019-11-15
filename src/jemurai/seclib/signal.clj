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
  (client/post "http://localhost:8000/signal" {:form-params signal :content-type :json})
  )

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

