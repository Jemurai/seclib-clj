# Jemurai Security Library (SecLib)

The Jemurai Security Library (for Clojure) is a client library that can be used by applications to implement certain simple security concepts and checks.

It can be coupled with our [signal service](https://github.com/jemurai/security-signal-service) to detect and collect security signal.

We use this code for training and as a starting point for building more tailored solutions as needed.  To say that another way, this is more of a proof of concept and example than a battle tested production library and using it will not by itself make your application more secure.  That said, there can be real value in doing the things illustrated here.

## Installation

Build from [source](https://github.com/Jemurai/seclib-clj) using `lein jar`.

TODO:  Build and push to clojars and update instructions for use if this gets to the point where it is worthwhile.

## Background and Usage

We typically differentiate Security Signal from Audit from Logging.

*  Signal - is specifically for capturing security signal which could be used to detect an attack.  An example might be a failed login.
*  Audit - audit records support providing evidence for an audit.  This facilitates a query and response around a question like "prove that you are capturing the assignment of privileges to users".
*  Logging - is for developers to identify potential issues.

We don't necessarily suggest that these should go to the same place.

###  Signaling

The signal functions provide a way for an applicaiton to report potential issues.

This is the basic way to report a security event.  The detail would include information about the event.  This can be called generically with a largely custom data.  The request is a way to get several basic details that can be useful later, like the remote IP.

```clojure
(signal/security-event request "Signal Event Detail" "Tags,Origination:Validation")
```

Alternatively, we can use helpers that make it eaiser to fill it in _and hint to developers what types of events they should be capturing_.

```clojure
(signal/login-event request "mkonda")
```

This example also shows the case where we create _both_ signal and audit events based on a provided event.  This is because a successful login is _both_ a security signal and an auditable item.

### Auditing

Remember that auditing is something we need to do to create a trail for later review and it typically maps to a particular standard.  So assume that there will be a table of events that an auditor can query by a control framework or control.

In a pattern similar to the signal, we offer a generic way to audit something.

```clojure
(signal/audit-event request "Audit Event Message" "Tags,CIS20:17,NIST800-53:AC-4,NIST-CSF-A-D-C-AC-1")
```

We also provide tailored helper methods to make it easier to capture the audit information we really need.

```clojure
(signal/data-access-event request subject action data)
```

TODO:  Consider mapping to a framework to make it easy to see that logging is happening for key controls.

### Validation

The seclib includes a validaiton library which provides _basic_ validation functions.  These are intended to illustrate how validation should be implemented but can also be the starting point for a common application validation library.  One benefit is that callers can easily and automateically create signal when there are failed validations, which is a Good Idea™.

Here is a basic example of using validation:

```clojure
(v/valid-alpha-string? "abc")
```

Here is a more detailed example where we want to do the same thing but produce a signal if it fails:

```clojure 
(v/validate-and-signal valid-alpha-string? "abc")
```

The validation library is a work in progress and we welcome pull requests with improvements and new validations.

### Detection

The idea behind detection is to make it easy to wire in detection of common malicious inputs.  You might ask yourself if these are necessary given potential other tooling, (eg. WAF, RASP, etc.) and our answer is that we simply want to extend the idea of signal to provide it for cases where input is an obvious attack.

The items supported by detection are only the obvious input that a scanner or clearly malicious attacker might clumsily supply to your application.  Chances are that you are getting these now and just producing error logs to the application logs that no-one sees.  Having even just a few simple detections enables you to produce a securiy signal.

As of now, you have to explicitly ask detection to run, but similar to validation, you can produce signal in one step: 

```clojure
(detect-on-string input-to-check)
```

See the tests for more examples of how to call the different functions.

## Options

TODO:  Add configuration for how to connect to signal service.

