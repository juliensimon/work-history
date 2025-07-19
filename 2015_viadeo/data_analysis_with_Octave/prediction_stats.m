
% load real and predicted data
real=csvread('dataset_reference.csv');
pred=csvread('result.csv');

premium_column = 6;

% remove first line (column names)
pred(1,:)=[];

% Count premium and non-premiums

real_non_premiums = find(real(:, premium_column)==0);
real_premiums     = find(real(:, premium_column)==1);

pred_non_premiums = find(pred(:, premium_column)==0);
pred_premiums     = find(pred(:, premium_column)==1);

fprintf("Real premiums          : %d\n", real_premiums);
fprintf("Predicted premiums     : %d\n", pred_premiums);
fprintf("Real non-premiums      : %d\n", real_non_premiums);
fprintf("Predicted non-premiums : %d\n", pred_non_premiums);
