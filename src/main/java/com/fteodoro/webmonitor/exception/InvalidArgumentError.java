package com.fteodoro.webmonitor.exception;

import java.util.Map;

public record InvalidArgumentError(int statusCode, Map<String, String> fields) {
}
