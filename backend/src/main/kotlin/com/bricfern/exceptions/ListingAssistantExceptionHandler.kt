package com.bricfern.exceptions;

class EmptyDescriptionException(message: String) : Exception(message)

class AiServiceException(message: String, cause: Throwable? = null) : Exception(message, cause)

class AiTimeoutException(message: String, cause: Throwable? = null) : Exception(message, cause)

class InvalidAiResponseException(message: String, cause: Throwable? = null) : Exception(message, cause)
