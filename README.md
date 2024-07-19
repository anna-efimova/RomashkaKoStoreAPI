### REST API Endpoints
This document describes the REST API endpoints for the RomashkaStoreAPI. Each endpoint's method, path, description, request parameters, and response formats are detailed below

also view swager
http://localhost:8080/swagger-ui/index.html


### Products API

- GET /api/products
Description: Retrieve all products.
Responses:
200 OK: Returns a list of products.
          
- GET /api/products/filter
Description: Retrieve products with optional filtering and sorting.
Parameters:
name (optional, string, max length 255): Filter by product name or part of it.
priceFrom (optional, number, positive or zero): Filter by minimum price.
priceTo (optional, number, positive or zero): Filter by maximum price.
inStock (optional, boolean): Filter by stock availability.
page (optional, number): Page number for pagination.
size (optional, number): Page size for pagination.
sort (optional, string): Sorting criteria, e.g., name,asc or price,desc.
Responses:
200 OK: Returns a list of filtered and/or sorted products.

- POST /api/products
Description: Create a new product.
Request Body:
ProductCreateDTO:
name (string, required, max length 255)
description (string, max length 4096)
price (number, positive or zero)
inStock (boolean)
Responses:
201 Created: Returns the created product.

- PUT /api/products/{id}
Description: Update an existing product.
Path Parameters:
id (required, long): ID of the product to update.
Request Body:
ProductCreateDTO:
name (string, required, max length 255)
description (string, max length 4096)
price (number, positive or zero)
inStock (boolean)
Responses:
200 OK: Returns the updated product.

- GET /api/products/{id}
Description: Retrieve a product by ID.
Path Parameters:
id (required, long): ID of the product to retrieve.
Responses:
200 OK: Returns the product with the specified ID.

- DELETE /api/products/{id}
Description: Delete a product by ID.
Path Parameters:
id (required, long): ID of the product to delete.
Responses:
204 No Content: Product successfully deleted.

### Sales API

- GET /api/sales/{id}
Description: Retrieve a sale by ID.
Path Parameters:
id (required, long): ID of the sale to retrieve.
Responses:
200 OK: Returns the sale with the specified ID.

- POST /api/sales
Description: Create a new sale.
Request Body:
SaleCreateDTO:
documentName (string, required, max length 255)
productId (required, long)
quantity (required, number, positive)
salePrice (required, number, positive)
Responses:
201 Created: Returns the created sale.

- PUT /api/sales/{id}
Description: Update an existing sale.
Path Parameters:
id (required, long): ID of the sale to update.
Request Body:
SaleCreateDTO:
documentName (string, required, max length 255)
productId (required, long)
quantity (required, number, positive)
salePrice (required, number, positive)
Responses:
200 OK: Returns the updated sale.

- DELETE /api/sales/{id}
Description: Delete a sale by ID.
Path Parameters:
id (required, long): ID of the sale to delete.
Responses:
204 No Content: Sale successfully deleted.

### Supplies API

- GET /api/supplies/{id}
Description: Retrieve a supply by ID.
Path Parameters:
id (required, long): ID of the supply to retrieve.
Responses:
200 OK: Returns the supply with the specified ID.

- POST /api/supplies
Description: Create a new supply.
Request Body:
SupplyCreateDTO:
documentName (string, required, max length 255)
productId (required, long)
quantity (required, number, positive)
Responses:
201 Created: Returns the created supply.

- PUT /api/supplies/{id}
Description: Update an existing supply.
Path Parameters:
id (required, long): ID of the supply to update.
Request Body:
SupplyCreateDTO:
documentName (string, required, max length 255)
productId (required, long)
quantity (required, number, positive)
Responses:
200 OK: Returns the updated supply.

- DELETE /api/supplies/{id}
Description: Delete a supply by ID.
Path Parameters:
id (required, long): ID of the supply to delete.
Responses:
204 No Content: Supply successfully deleted.

