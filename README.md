# Jemurai Security Library (SecLib)

The Jemurai Security Library (for Clojure) provides simple security primitive functions that many applications may want to use.  These include validation and security signal propogation.

Validation includes: 
* email validation
* numeric validation
* alpha validation
* TODO:  Others

Signal includes the ability to collect and forward signals to a signal service.
* Security Signals
* Audit Signals
* Exception Signals

## Installation

Build from source https://github.com/Jemurai/seclib-clj using `lein jar`.

TODO:  Build and push to clojars and update instructions for use if this gets to the point where it is worthwhile.

## Usage

Use the validation methods after importing.

Use the meta methods to flexibly do both validation and signal propogation.

## Options

TODO:  Add configuration for how to connect to signal service.


