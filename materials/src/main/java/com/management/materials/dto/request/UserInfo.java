package com.management.materials.dto.request;

/**
 * DTO para información del usuario
 */
public  class UserInfo {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String[] roles;
    private String[] permissions;

    public UserInfo() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String[] getRoles() { return roles; }
    public void setRoles(String[] roles) { this.roles = roles; }

    public String[] getPermissions() { return permissions; }
    public void setPermissions(String[] permissions) { this.permissions = permissions; }
}