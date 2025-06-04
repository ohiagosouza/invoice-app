# Invoice Application
## Description
This is the invoice app backend 

## UML
```mermaid
classDiagram
    class User {
        +String id
        +String name
        +String phoneNumber
        +String email
        +String password
        +String document // CPF/CNPJ
    }

    class Address {
        +String street
        +String number
        +String district
        +String complement
        +String city
        +String state
        +String country
    }

    class Client {
        +String id
        +String name
        +String phoneNumber
        +String email
        +String password
        +String document // CPF/CNPJ
        +String ownerId
    }

    class Product {
        +String id
        +String name
        +String description
        +BigDecimal price
        +String category
        +int stock
    }

    class Invoice {
        +String id
        +String clientId
        +String userId
        +LocalDateTime date
        +List~ProductItem~ items
        +BigDecimal discount
        +BigDecimal total
    }

    class ProductItem {
        +String productId
        +String name
        +int quantity
        +BigDecimal unitPrice
        +BigDecimal subtotal
    }

    class AuthRequest {
        +String email
        +String password
    }

    class AuthResponse {
        +String token
    }

    class JwtUtil {
        +String generateToken(User user)
        +boolean validateToken(String token)
        +String extractUsername(String token)
    }

    User "1" --> "*" Client : manages
    User "1" *-- "1" Address : has
    User "1" --> "*" Product : owns
    User "1" --> "*" Invoice : creates
    Client "1" *-- "1" Address : has
    Invoice "1" --> "*" ProductItem : contains
    Invoice "1" --> "1" Client : belongsTo
```