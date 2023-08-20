const express = require('express');
const axios = require('axios');
const sqlite3 = require('sqlite3').verbose();
const path = require('path'); 
const app = express();
const PORT = process.env.PORT || 3000;

// Configura o EJS
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

// Rota para a página que lista os produtos
app.get('/products', async (req, res) => {
  try {
    const response = await axios.get('http://localhost:3000/api/products');
    const products = response.data;

    // Lógica de paginação
    const perPage = 10; // Número de produtos por página
    const page = req.query.page || 1;
    const startIndex = (page - 1) * perPage;
    const endIndex = startIndex + perPage;
    const paginatedProducts = products.slice(startIndex, endIndex);
    const nextPage = products.length > endIndex ? `/products?page=${parseInt(page) + 1}` : null;

    res.render('products', { products: paginatedProducts, nextPage });
  } catch (error) {
    console.error('Erro ao obter produtos:', error);
    res.status(500).send('Erro ao obter produtos.');
  }
});

// Conecta ao banco de dados SQLite em memória
const dbConnection = new sqlite3.Database(':memory:', (err) => {
  if (err) {
    console.error('Erro ao conectar ao banco de dados:', err);
  } else {
    console.log('Conectado ao banco de dados');
    // Cria tabela no banco de dados
    dbConnection.run(`
      CREATE TABLE IF NOT EXISTS products (
        id INTEGER PRIMARY KEY,
        brand TEXT,
        name TEXT,
        image_link TEXT,
        price TEXT
      )
    `);
  }
});

// Função para armazenar dados no banco de dados
function cacheDataInDB(products) {
  return new Promise((resolve, reject) => {
    const stmt = dbConnection.prepare('INSERT INTO products (brand, name, image_link, price) VALUES (?, ?, ?, ?)');
    products.forEach(product => {
      stmt.run(product.brand, product.name, product.image_link, product.price);
    });
    stmt.finalize((err) => {
      if (err) {
        reject(err);
      } else {
        resolve();
      }
    });
  });
}

// Função para obter dados do banco de dados
function getCachedDataFromDB() {
  return new Promise((resolve, reject) => {
    dbConnection.all('SELECT * FROM products', (err, rows) => {
      if (err) {
        reject(err);
      } else {
        resolve(rows);
      }
    });
  });
}

// Endpoint para obter produtos e exibí-los
app.get('/api/products', async (req, res) => {
  try {
    const cachedData = await getCachedDataFromDB();
    if (cachedData.length > 0) {
      console.log('Dados recuperados do banco de dados.');
      return res.json(cachedData);
    }

    const response = await axios.get('https://makeup-api.herokuapp.com/api/v1/products.json');
    const products = response.data;

    await cacheDataInDB(products);

    return res.json(products);
  } catch (error) {
    console.error('Erro ao obter produtos:', error);
    return res.status(500).json({ error: 'Erro ao obter produtos.' });
  }
});
app.get('/products', async (req, res) => {
  try {
    const response = await axios.get('http://localhost:3000/api/products');
    const allProducts = response.data;

    const { type, brand, color, minPrice, maxPrice } = req.query;

    // Aplicar filtros caso estiverem presentes nas query strings
    let filteredProducts = allProducts;

    if (type) {
      filteredProducts = filteredProducts.filter(product => product.product_type.toLowerCase() === type.toLowerCase());
    }

    if (brand) {
      filteredProducts = filteredProducts.filter(product => product.brand.toLowerCase() === brand.toLowerCase());
    }

    if (color) {
      filteredProducts = filteredProducts.filter(product => product.product_colors.includes(color));
    }

    if (minPrice) {
      filteredProducts = filteredProducts.filter(product => parseFloat(product.price) >= parseFloat(minPrice));
    }

    if (maxPrice) {
      filteredProducts = filteredProducts.filter(product => parseFloat(product.price) <= parseFloat(maxPrice));
    }

    // Lógica de paginação (igual à anterior...)
    // ...

    res.render('products', { products: paginatedProducts, nextPage });
  } catch (error) {
    console.error('Erro ao obter produtos:', error);
    res.status(500).send('Erro ao obter produtos.');
  }
});



app.listen(PORT, () => {
  console.log(`Servidor rodando na porta ${PORT}`);
});
