(ns jemurai.seclib.signal
  (:require [clj-http.client :as client])
  (:gen-class))

(defrecord Signal [type timestamp remote-ip message tags])

; This only works when the signal service is actually running!
(defn report-signal
  [event]
  (client/post "http://localhost:8000/signal" {:form-params event :content-type :json})
  )

(defn security-event
  "Build the security event."
  [request tags]
  (def signal (->Signal "Security Event" (System/currentTimeMillis) "127.0.0.1" request tags))
  (report-signal signal)
  false)

(defn audit-event
  "Build the audit event."
  [request tags]
  (def signal (->Signal "Audit Event" (System/currentTimeMillis) "127.0.0.1" request tags))
  (report-signal signal)
  false)

(defn exception-event
  "Build the security event."
  [request tags]
  (report-signal
   {:event "Exception"
    :request request
    :time "Now"
    :remote-ip "127.0.0.1"
    :tags tags} )
  false)

