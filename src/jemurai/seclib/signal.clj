(ns jemurai.seclib.signal
  (:require [clj-http.client :as client])
  (:gen-class))

(defrecord Signal [type timestamp remote-ip message])

; This only works when the signal service is actually running!
(defn report
  [event]
  (client/post "http://localhost:8000/signal" {:form-params event :content-type :json})
  )

(defn security-event
  "Build the security event."
  [request]
  (def signal (->Signal "Security Event" (System/currentTimeMillis) "127.0.0.1" request))
  (report signal)
  false)

  ;
;  {:request request
;   :time "Now"
;   :remote-ip "127.0.0.1"})
;  false)

(defn exception-event
  "Build the security event."
  [request]
  (report
   {:event "Exception"
    :request request
    :time "Now"
    :remote-ip "127.0.0.1"}))
