package com.kawahedukasi.util;

import com.kawahedukasi.model.User;
import io.smallrye.jwt.build.Jwt;

import java.util.HashSet;
import java.util.List;

public class JwtUtil {
    public static String generate(User user){
        return Jwt.issuer("https://example.com/issuer")
                .upn(user.getUsername())
                .groups(new HashSet<>(List.of(user.getRole())))
                .claim("name", user.getName())
                .sign();
    }
}
