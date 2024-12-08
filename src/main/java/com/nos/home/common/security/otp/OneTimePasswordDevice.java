package com.nos.home.common.security.otp;


import java.util.UUID;

public interface OneTimePasswordDevice
{
    UUID getId();

    String getName();

    String getSecret();

    boolean confirm(String code);

    boolean confirmed();

    boolean accepts(String code);
}
