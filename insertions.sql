INSERT INTO `order` VALUES 
(1, 'ef4f7b2e-cce3-11ef-b916-b48c9d6df3cd', '2025-01-01 00:00:00', '2025-01-02 00:00:00', 'PENDING', 100.5, 1, '4b357396-cce4-11ef-b916-b48c9d6df3cd', 'PRODUCT', 1, '45e2ef5e-cce4-11ef-b916-b48c9d6df3cd', 'First order description'),
(2, 'ef4f8184-cce3-11ef-b916-b48c9d6df3cd', '2025-01-03 00:00:00', '2025-01-04 00:00:00', 'FINISHED', 250, 2, '4b3579db-cce4-11ef-b916-b48c9d6df3cd', 'APPARTMENT', 2, '45e2f5ae-cce4-11ef-b916-b48c9d6df3cd', 'Second order description'),
(3, 'ef4f8326-cce3-11ef-b916-b48c9d6df3cd', '2025-01-05 00:00:00', '2025-01-06 00:00:00', 'CANCELLED', 75.75, 3, '4b357b93-cce4-11ef-b916-b48c9d6df3cd', 'PRODUCT', 3, '45e2f864-cce4-11ef-b916-b48c9d6df3cd', 'Third order description'),
(4, 'ef4f8568-cce3-11ef-b916-b48c9d6df3cd', '2025-01-07 00:00:00', '2025-01-08 00:00:00', 'CANCELLED', 180, 4, '4b357d45-cce4-11ef-b916-b48c9d6df3cd', 'SERVICE', 4, '45e2fa78-cce4-11ef-b916-b48c9d6df3cd', 'Fourth order description'),
(5, 'ef4f879e-cce3-11ef-b916-b48c9d6df3cd', '2025-01-09 00:00:00', '2025-01-10 00:00:00', 'CANCELLED', 300, 5, '4b357eb8-cce4-11ef-b916-b48c9d6df3cd', 'ELECTRONIC', 5, '45e2fbba-cce4-11ef-b916-b48c9d6df3cd', 'Fifth order description'),
(6, 'ef4f8ae2-cce3-11ef-b916-b48c9d6df3cd', '2025-01-11 00:00:00', '2025-01-12 00:00:00', 'PENDING', 400, 6, '4b35802a-cce4-11ef-b916-b48c9d6df3cd', 'SUBSCRIPTION', 6, '45e2fd12-cce4-11ef-b916-b48c9d6df3cd', 'Sixth order description'),
(7, 'ef4f8d28-cce3-11ef-b916-b48c9d6df3cd', '2025-01-13 00:00:00', '2025-01-14 00:00:00', 'FINISHED', 120.9, 7, '4b3581be-cce4-11ef-b916-b48c9d6df3cd', 'FOOD', 7, '45e2fe6c-cce4-11ef-b916-b48c9d6df3cd', 'Seventh order description'),
(8, 'ef4f8f6a-cce3-11ef-b916-b48c9d6df3cd', '2025-01-15 00:00:00', '2025-01-16 00:00:00', 'CANCELLED', 500.5, 8, '4b358350-cce4-11ef-b916-b48c9d6df3cd', 'TRAVEL', 8, '45e2ffb4-cce4-11ef-b916-b48c9d6df3cd', 'Eighth order description'),
(9, 'ef4f91c4-cce3-11ef-b916-b48c9d6df3cd', '2025-01-17 00:00:00', '2025-01-18 00:00:00', 'PENDING', 200, 9, '4b3584f0-cce4-11ef-b916-b48c9d6df3cd', 'BOOK', 9, '45e3012e-cce4-11ef-b916-b48c9d6df3cd', 'Ninth order description'),
(10, 'ef4f93fe-cce3-11ef-b916-b48c9d6df3cd', '2025-01-19 00:00:00', '2025-01-20 00:00:00', 'PENDING', 320.75, 10, '4b35867e-cce4-11ef-b916-b48c9d6df3cd', 'FURNITURE', 10, '45e30296-cce4-11ef-b916-b48c9d6df3cd', 'Tenth order description');

INSERT INTO `transaction` VALUES 
(1, 'f1714d93-cce3-11ef-b916-b48c9d6df3cd', 100.5, 'CARD', 1, 'SUCCESS', '2025-01-01 00:00:00', '2025-01-02 00:00:00', 'Transaction for the first order'),
(2, 'f17152ea-cce3-11ef-b916-b48c9d6df3cd', 250, 'PAYPAL', 2, 'FAILED', '2025-01-03 00:00:00', '2025-01-04 00:00:00', 'Transaction for the second order'),
(3, 'f1715454-cce3-11ef-b916-b48c9d6df3cd', 75.75, 'CASH', 3, 'PENDING', '2025-01-05 00:00:00', '2025-01-06 00:00:00', 'Transaction for the third order'),
(4, 'f17156a8-cce3-11ef-b916-b48c9d6df3cd', 180, 'CARD', 4, 'SUCCESS', '2025-01-07 00:00:00', '2025-01-08 00:00:00', 'Transaction for the fourth order'),
(5, 'f17158ec-cce3-11ef-b916-b48c9d6df3cd', 300, 'TRANSFER', 5, 'SUCCESS', '2025-01-09 00:00:00', '2025-01-10 00:00:00', 'Transaction for the fifth order'),
(6, 'f1715c3d-cce3-11ef-b916-b48c9d6df3cd', 400, 'TRANSFER', 6, 'PENDING', '2025-01-11 00:00:00', '2025-01-12 00:00:00', 'Transaction for the sixth order'),
(7, 'f1715e7f-cce3-11ef-b916-b48c9d6df3cd', 120.9, 'CARD', 7, 'SUCCESS', '2025-01-13 00:00:00', '2025-01-14 00:00:00', 'Transaction for the seventh order'),
(8, 'f17160b6-cce3-11ef-b916-b48c9d6df3cd', 500.5, 'PAYPAL', 8, 'FAILED', '2025-01-15 00:00:00', '2025-01-16 00:00:00', 'Transaction for the eighth order'),
(9, 'f17162ee-cce3-11ef-b916-b48c9d6df3cd', 200, 'CASH', 9, 'PENDING', '2025-01-17 00:00:00', '2025-01-18 00:00:00', 'Transaction for the ninth order'),
(10, 'f1716537-cce3-11ef-b916-b48c9d6df3cd', 320.75, 'CARD', 10, 'SUCCESS', '2025-01-19 00:00:00', '2025-01-20 00:00:00', 'Transaction for the tenth order');
