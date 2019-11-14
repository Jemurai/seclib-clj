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

(deftest number-valid-no-test
  (testing "Test that number validator correctly rejects a number of bad number examples."
    (and
     (is (= false (valid-number? "m")))
     (is (= false (valid-number? "@1")))
     (is (= false (valid-number? "o"))))))

(deftest number-valid-yes-test
  (testing "Test that number validator correctly accepts a number of valid number examples."
    (and
     (is (= true (valid-number? "1")))
     (is (= true (valid-number? "1.3")))
     (is (= true (valid-number? "1.444444"))))))

(deftest alpha-valid-yes-test
  (testing "Test that number validator correctly accepts a number of valid number examples."
    (and
     (is (= true (valid-alpha-string? "abc")))
     (is (= true (valid-alpha-string? "def")))
     (is (= true (valid-alpha-string? "abd"))))))

(deftest meta-validate
  (testing "Test that the validate function works."
    (and
     (is (= true (validate valid-alpha-string? "abc")))
     (is (= true (validate valid-number? "13")))
     (is (= false (validate valid-email? "abd")))))
  )

(deftest meta-validate-and-signal
  (testing "Test that the validate function works."
    (and
     (is (= true (validate-and-signal valid-alpha-string? "abc")))
     (is (= true (validate-and-signal valid-number? "13")))
     (is (= false (validate-and-signal valid-email? "abd"))))))