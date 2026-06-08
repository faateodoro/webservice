package com.fteodoro.webmonitor.exception;

public record ResponseError(int statusCode, String message) {}
