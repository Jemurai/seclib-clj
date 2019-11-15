(ns jemurai.seclib.signal-test
  (:require [clojure.test :refer :all]
            [jemurai.seclib.signal :as signal]))

(deftest a-signal-test
  (testing "Test sending signal."
    (is (= false (signal/security-event "Signal Request" "Tags,Origination:Validation")))))

(deftest an-audit-test
  (testing "Test sending an audit signal."
    (is (= false (signal/audit-event "Audit Request" "Tags,CIS20:17,NIST800-53:AC-4,NIST-CSF-A-D-C-AC-1")))))

(deftest an-exception-test
  (testing "Test sending an exception to signal."
    (is (= false (signal/exception-event "Exception Request" "Tags")))))
