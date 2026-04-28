import { rest } from 'msw';

export const handlers = [
  rest.get('*/api/v1/products', (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        items: [
          { id: 1, name: 'Product 1', price: 100 },
          { id: 2, name: 'Product 2', price: 200 }
        ]
      })
    );
  })
];
