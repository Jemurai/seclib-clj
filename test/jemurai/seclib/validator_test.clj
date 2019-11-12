(ns jemurai.seclib.validator-test
  (:require [clojure.test :refer :all]
            [jemurai.seclib.validator :refer :all]))

(deftest email-valid-yes-test
  (testing "Test that email validator correctly validates a number of good email examples."
    (and
     (is (= true (valid-email? "mkonda@yo.com")))
     (is (= true (valid-email? "mkonda@jemurai.com"))) 
     (is (= true (valid-email? "mkonda@yo.co.uk")))
     )))

(deftest email-valid-no-test
  (testing "Test that email validator correctly rejects a number of bad email examples."
    (and

     (is (= false (valid-email? "mkonda@1")))
     (is (= false (valid-email? "mko@da@1")))
     (is (= false (valid-email? "mkonda@yo."))))))