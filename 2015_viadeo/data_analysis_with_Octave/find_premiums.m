data = csvread("event_breakdown_by_member_90days.csv");
premium = csvread("premium_members.csv");

[x,data_premium_idx,premium_idx] = intersect(data(:,1),premium);
[x,data_non_premium_idx] = setdiff(data(:,1), premium);

data_premium = data(data_premium_idx,:);
data_non_premium = data(data_non_premium_idx, :);


