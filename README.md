gwt-dispatch
============

Provides a centralized communication channel between GWT client and server, 
so that the RPC is encapsulated making it easy to set up both centralized 
exception handling in the client (eg. for threating autentication exceptions, 
etc) and filter-like aspects for the requests in the server.

This lib also encourages the use of good practices and is 
a great choice if you want to make use of Command/Action patterns.

Originally was hosted at google code as [drycode-gwt-dispatch-service][1]

The project is in production for a few years for a number of private projects,
working smoothly. It is unlikely that it will keep being updated as it already
satisfied its goals.

[1]: https://code.google.com/p/drycode-gwt-dispatch-service/


### Some use cases

##### Centralized error handling

There are many cases where multiple RPC methods are susceptible to errors and exceptions that require centralized handling.
Every one of these methods could be explicitly programmed check the occurrence of such, but in most cases, handling those
in such manner goes way against encapsulation: be it by spreading the responsibility of handling a problem throughout the app
or by the need of making a centralized handling service "visible and accessble" where it shouldn't be otherwise.

By encapsulating RPC itself, it is possible to register exception handlers that should act upon failure of any RPC call.
This incredibly simple solution enables, for instance, straightforward authentication error handling, which could then
direct the user to a login page.

Other very similar examples would be handling authorization errors or even dealing with connections errors.


##### Filter-like RPC aspects

Servlet filters are a great feature for implementing some application concerns,
but when you need to apply them distinguishing RPC calls you'd probably end up with
hackish solutions...

This lib can help you with that. This feature can, for example, be used to implement
server side authorization check, in which a filter could:

- could set permission related settings on the user session;
- verify the user session agains the RPC call itself;
- ... whatever your imagination can reach ... one project I've participated used itself
to set some user settings, that - on its turn - were used by validation AOP aspects
before the execution of business methods.


##### Special RPC requests

When RPC calls require special headers, parameters, ..., this lib could help you out a lot. You could configure a RPC factory of
you linking. This feature has already been used to achieve many neat things, including an simple "per-tab/window session" differentitation,
done by the creation of an unique - tab/window - instance id that was always passed in message headers.


##### ...

