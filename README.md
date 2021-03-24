# ktor-bug

## Description

This is a repo demonstrating how `call.receive<T>` doesn't throw `ContentTransformationException` when it receives an incorrect json request, instead it throws a `JsonDecodingException`.

The documentation comment of `call.receive<T>` states the following:
```kt
/**
 * Receives content for this request.
 * @return instance of [T] received from this call.
 * @throws ContentTransformationException when content cannot be transformed to the requested type.
 */
@OptIn(ExperimentalStdlibApi::class)
public suspend inline fun <reified T : Any> ApplicationCall.receive(): T = receive(typeOf<T>())
```

My reading of this is that if the json content is malformed or does not match the provided type `T`, a `ContentTransformationException` will be thrown. 

In case the case that throwing `JsonDecodingException` is the intended behaviour, maybe a rewording could be considered for the documentation of this function.

## How to reproduce

1. Open the project in IntelliJ.
2. Start the ktor server in debug.
3. Place a breakingpoint on line 32 in Application.kt
4. Run the http request from tests/http/test.http.
5. You will now notice in the debugger that the exception `e` is of type `JsonDecodingException` instead of `ContentTransformationException`. 

If you check the request from tests/http/test.http you will notice that it sends a json with a typo, the field is named "dataa" but the server expects it to be called "data".
The json is however valid and sending invalid json also results in a `JsonDecodingException`.
